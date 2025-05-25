package com.app.example.order.application.usecase;

import com.app.example.order.application.command.CancelOrderCommand;
import com.app.example.order.domain.enums.OrderStatusEnum;
import com.app.example.order.infra.repository.OrderRepository;
import com.app.example.shared.exception.ProductNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável pelo cancelamento de um pedido.
 * <p>
 * Este caso de uso define a lógica de negócios para cancelar um pedido no sistema.
 * Ele implementa a interface {@link IUseCase} e é responsável por processar os dados de entrada
 * e retornar o resultado correspondente após a execução da lógica de cancelamento de pedidos.
 * <p>
 * O método {@link #execute(CancelOrderCommand.Input)} é responsável por realizar a execução do processo
 * de cancelamento de um pedido, retornando a saída necessária para o fluxo do sistema.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * CancelOrderUseCase cancelOrderUseCase = applicationContext.getBean(CancelOrderUseCase.class);
 * Mono<CancelOrderCommand.Output> result = cancelOrderUseCase.execute(input);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("cancelOrderUseCase")
public class CancelOrderUseCase implements IUseCase<CancelOrderCommand.Input, Mono<CancelOrderCommand.Output>> {

    private final OrderRepository orderRepository;

    /**
     * Executa o caso de uso de cancelamento de pedido.
     * <p>
     * Este método recebe os dados de entrada para o cancelamento de um pedido e executa a lógica
     * de cancelamento do pedido. A lógica de negócios para o cancelamento do pedido será implementada
     * neste método.
     *
     * @param input Dados de entrada necessários para cancelar o pedido, como o identificador do pedido.
     * @return Retorna um Mono com a saída do comando de cancelamento do pedido.
     */
    @Override
    public Mono<CancelOrderCommand.Output> execute(CancelOrderCommand.Input input) {
        return orderRepository.findById(UUID.fromString(input.orderId()))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Pedido com ID {} não encontrado", input.orderId());
                    return Mono.error(new ProductNotFoundException("Pedido não encontrado com o id: " + input.orderId()));
                }))
                .map(orderCancel -> {
                    orderCancel.setStatus(OrderStatusEnum.CANCELED.name());
                    return orderRepository.save(orderCancel);
                })
                .thenReturn(new CancelOrderCommand.Output(input.orderId(), "Pedido cancelado com sucesso"));
    }
}
