package com.app.example.order.application.usecase;

import com.app.example.order.application.command.ShipOrderCommand;
import com.app.example.order.domain.enums.OrderStatusEnum;
import com.app.example.order.domain.event.OrderShipped;
import com.app.example.order.infra.repository.OrderRepository;
import com.app.example.shared.exception.ProductNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável pelo envio de um pedido.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("shipOrderUseCase")
public class ShipOrderUseCase implements IUseCase<ShipOrderCommand.Input, Mono<ShipOrderCommand.Output>> {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    /**
     * Executa o caso de uso de envio de um pedido.
     *
     * @param input Dados de entrada necessários para enviar o pedido, como o identificador do pedido.
     * @return Retorna um Mono com a saída do comando de envio do pedido.
     */
    @Override
    public Mono<ShipOrderCommand.Output> execute(ShipOrderCommand.Input input) {
        UUID orderId = UUID.fromString(input.orderId());

        return orderRepository.findById(orderId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Pedido com ID {} não encontrado", input.orderId());
                    return Mono.error(new ProductNotFoundException("Pedido não encontrado com o id: " + input.orderId()));
                }))
                .flatMap(order -> {
                    order.setStatus(OrderStatusEnum.SHIPPED.name());
                    return orderRepository.save(order)
                            .doOnSuccess(saved -> {
                                log.info("Pedido {} enviado com sucesso. Publicando evento OrderShipped...", input.orderId());
                                publisher.publishEvent(new OrderShipped(orderId));
                            });
                })
                .thenReturn(new ShipOrderCommand.Output(input.orderId(), "Pedido enviado com sucesso"));
    }
}
