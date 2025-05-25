package com.app.example.order.application.usecase;

import com.app.example.order.application.command.FindByIdCommand;
import com.app.example.order.infra.repository.OrderRepository;
import com.app.example.order.mapper.FindByIdMapper;
import com.app.example.shared.exception.OrderNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para buscar um pedido por ID.
 * <p>
 * Executa a lógica de recuperação de um pedido a partir do repositório
 * e transforma os dados para a saída esperada pelo cliente.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("cancelOrderUseCase")
public class FindByIdUseCase implements IUseCase<FindByIdCommand.Input, Mono<FindByIdCommand.Output>> {

    private final OrderRepository orderRepository;
    private final FindByIdMapper findByIdMapper;

    /**
     * Executa a busca de um pedido pelo seu ID.
     *
     * @param input objeto contendo o ID do pedido a ser buscado.
     * @return {@link Mono} contendo os dados do pedido, ou erro se não encontrado.
     */
    @Override
    public Mono<FindByIdCommand.Output> execute(FindByIdCommand.Input input) {
        log.info("Iniciando busca de pedido com ID: {}", input.orderId());

        return orderRepository.findById(input.orderId())
                .doOnNext(order -> log.debug("Pedido encontrado: {}", order))
                .map(findByIdMapper::toOutput)
                .doOnNext(output -> log.info("Pedido mapeado para saída: {}", output))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Pedido não encontrado com ID: {}", input.orderId());
                    return Mono.error(new OrderNotFoundException(input.orderId()));
                }));
    }
}