package com.app.example.product.application.usecase;

import com.app.example.product.application.commnad.GetStockCommand;
import com.app.example.product.infra.repository.ProductRepository;
import com.app.example.product.mapper.GetStockMapper;
import com.app.example.shared.exception.ProductNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso para obter o estoque de um produto.
 * <p>
 * Este caso de uso consulta o repositório de produtos com base no ID fornecido
 * e retorna a quantidade disponível em estoque.
 * </p>
 * Lança uma exceção se o produto não for encontrado.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("getStockUseCase")
public class GetStockUseCase implements IUseCase<GetStockCommand.Input, Mono<GetStockCommand.Output>> {

    private final ProductRepository productRepository;
    private final GetStockMapper getStockMapper;

    /**
     * Executa a lógica para buscar o estoque de um produto.
     *
     * @param input os dados de entrada contendo o ID do produto
     * @return um Mono com os dados de saída contendo o estoque disponível
     * @throws ProductNotFoundException se o produto não for encontrado
     */
    @Override
    public Mono<GetStockCommand.Output> execute(GetStockCommand.Input input) {
        UUID id = UUID.fromString(input.productId());
        log.info("Iniciando consulta de estoque para o produto com ID: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    log.info("Produto encontrado: {}", product.getId());
                    return getStockMapper.toOutput(product);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Produto com ID {} não encontrado.", id);
                    return Mono.error(new ProductNotFoundException("Produto não encontrado com ID: " + id));
                }));
    }
}