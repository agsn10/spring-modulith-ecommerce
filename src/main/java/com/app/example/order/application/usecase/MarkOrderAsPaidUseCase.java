package com.app.example.order.application.usecase;

import com.app.example.order.application.command.MarkOrderAsPaidCommand;
import com.app.example.order.domain.enums.OrderStatusEnum;
import com.app.example.order.infra.repository.OrderRepository;
import com.app.example.payment.application.ppi.PaymentPort;
import com.app.example.payment.domain.dto.GetPaymentDTO;
import com.app.example.payment.domain.enums.PaymentStatusEnum;
import com.app.example.shared.exception.PaymentNotApprovedException;
import com.app.example.shared.exception.ProductNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável pela marcação de um pedido como pago.
 * <p>
 * Este caso de uso define a lógica de negócios para marcar um pedido como pago no sistema.
 * Ele implementa a interface {@link IUseCase} e é responsável por processar os dados de entrada
 * e retornar o resultado correspondente após a execução da lógica de pagamento do pedido.
 * <p>
 * O método {@link #execute(MarkOrderAsPaidCommand.Input)} é responsável por realizar a execução do processo
 * de marcação de pagamento do pedido, retornando a saída necessária para o fluxo do sistema.
 *
 * <p><b>Exemplo de uso:</b></p>
 * <pre>{@code
 * MarkOrderAsPaidUseCase markOrderAsPaidUseCase = applicationContext.getBean(MarkOrderAsPaidUseCase.class);
 * Mono<MarkOrderAsPaidCommand.Output> result = markOrderAsPaidUseCase.execute(input);
 * }</pre>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("markOrderAsPaidUseCase")
public class MarkOrderAsPaidUseCase implements IUseCase<MarkOrderAsPaidCommand.Input, Mono<MarkOrderAsPaidCommand.Output>> {

    private final OrderRepository orderRepository;
    private final PaymentPort paymentPort;

    /**
     * Executa o caso de uso de marcação de pedido como pago.
     * <p>
     * Este método recebe os dados de entrada para a marcação de pagamento de um pedido e executa a lógica
     * necessária para indicar que o pedido foi pago. A lógica de negócios para o pagamento do pedido será implementada
     * neste método.
     *
     * @param input Dados de entrada necessários para marcar o pedido como pago, como o identificador do pedido.
     * @return Retorna um Mono com a saída do comando de marcação do pedido como pago.
     */
    @Override
    public Mono<MarkOrderAsPaidCommand.Output> execute(MarkOrderAsPaidCommand.Input input) {
        return paymentPort.getPaymentByOrderId(new GetPaymentDTO.Request(input.orderId()))
                .flatMap(paymentResponse -> {
                    // Verifica se o status do pagamento é "APPROVED"
                    if (paymentResponse.status().equals(PaymentStatusEnum.APPROVED.name())) {
                        // Lança uma exceção se o pagamento não foi aprovado
                        return Mono.error(new PaymentNotApprovedException("Pagamento não foi aprovado para o pedido: " + input.orderId()));
                    }

                    // Se o pagamento foi aprovado, continua com o processamento do pedido
                    return orderRepository.findById(UUID.fromString(input.orderId()))
                            .switchIfEmpty(Mono.defer(() -> {
                                log.warn("Pedido com ID {} não encontrado", input.orderId());
                                return Mono.error(new ProductNotFoundException("Pedido não encontrado com o id: " + input.orderId()));
                            }))
                            .map(order -> {
                                // Atualiza o status do pedido para "PAID"
                                order.setStatus(OrderStatusEnum.PAID.name());
                                return orderRepository.save(order);
                            })
                            .thenReturn(new MarkOrderAsPaidCommand.Output(input.orderId(), "Pedido pago com sucesso"));
                });
    }
}