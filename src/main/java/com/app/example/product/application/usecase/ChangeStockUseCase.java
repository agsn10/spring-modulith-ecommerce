package com.app.example.product.application.usecase;

import com.app.example.product.application.commnad.ChangeStockCommand;
import com.app.example.product.domain.event.StockReduced;
import com.app.example.product.infra.repository.ProductRepository;
import com.app.example.shared.exception.ProductNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("changeStockUseCase")
public class ChangeStockUseCase implements IUseCase<ChangeStockCommand.Input, Mono<ChangeStockCommand.Output>> {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Mono<ChangeStockCommand.Output> execute(ChangeStockCommand.Input input) {
        UUID id = UUID.fromString(input.productId());
        log.info("Iniciando alteração de estoque para o produto com ID {}. Quantidade solicitada: {}", id, input.quantity());

        return productRepository.findById(id)
                .doOnNext(product -> log.debug("Produto encontrado: {} (estoque atual: {})", product.getName(), product.getStockQuantity()))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Produto com ID {} não encontrado", id);
                    return Mono.error(new ProductNotFoundException("Produto não encontrado"));
                }))
                .flatMap(product -> {
                    int newStock = product.getStockQuantity() - input.quantity();
                    if (newStock < 0) {
                        log.warn("Estoque insuficiente para o produto com ID {}. Estoque atual: {}, tentativa de retirada: {}", id, product.getStockQuantity(), -input.quantity());
                        return Mono.error(new IllegalArgumentException("Estoque insuficiente"));
                    }
                    log.info("Atualizando estoque do produto com ID {}. Novo estoque: {}", id, newStock);

                    return productRepository.updateStockQuantity(input.quantity(), id)
                            .flatMap(rowsUpdated -> {
                                if (rowsUpdated == 0) {
                                    log.error("Nenhuma linha foi atualizada ao tentar ajustar o estoque do produto com ID {}", id);
                                    return Mono.error(new IllegalStateException("Falha ao atualizar o estoque"));
                                }

                                // Publicar o evento somente se o estoque for reduzido
                                if (input.quantity() < 0) {
                                    log.info("Publicando evento StockReduced para produto ID {} com quantidade {}", id, -input.quantity());
                                    publisher.publishEvent(new StockReduced(id.toString(), -input.quantity()));
                                }

                                return Mono.just(new ChangeStockCommand.Output(
                                        input.productId(),
                                        newStock,
                                        "Estoque atualizado com sucesso"
                                ));
                            });
                });
    }
}
