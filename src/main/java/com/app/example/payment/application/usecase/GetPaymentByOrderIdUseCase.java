package com.app.example.payment.application.usecase;

import com.app.example.payment.application.command.GetPaymentCommand;
import com.app.example.payment.infra.repository.PaymentRepository;
import com.app.example.payment.mapper.GetPaymentMapper;
import com.app.example.shared.exception.PaymentNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável por buscar um pagamento existente a partir do ID de pedido.
 * <p>
 * Este caso de uso aplica a lógica de leitura da entidade {@code PaymentPO}, retornando um DTO
 * com os dados associados, ou lançando uma exceção se o pagamento não for encontrado.
 * </p>
 *
 * Implementa a interface genérica {@link IUseCase}, usando como entrada o {@link GetPaymentCommand.Input}
 * e como saída um {@link Mono} contendo {@link GetPaymentCommand.Output}.
 *
 * <p>
 * Exemplo de uso:
 * <pre>{@code
 * Mono<GetPaymentCommand.Output> resultado = getPaymentUseCase.execute(new Input("order-1234"));
 * }</pre>
 * </p>
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("getPaymentUseCase")
public class GetPaymentByOrderIdUseCase implements IUseCase<GetPaymentCommand.Input, Mono<GetPaymentCommand.Output>> {

    /** Repositório reativo para acesso a dados de pagamento. */
    private final PaymentRepository paymentRepository;

    /** Mapper responsável por converter {@code PaymentPO} em {@code GetPaymentCommand.Output}. */
    private final GetPaymentMapper getPaymentMapper;

    /**
     * Executa o caso de uso de busca de pagamento a partir do ID do pedido.
     *
     * @param input objeto contendo o {@code orderId} do pagamento a ser buscado
     * @return um {@link Mono} com os dados do pagamento encapsulados em {@link GetPaymentCommand.Output}
     * @throws PaymentNotFoundException se nenhum pagamento for encontrado com o {@code orderId} fornecido
     */
    @Override
    public Mono<GetPaymentCommand.Output> execute(GetPaymentCommand.Input input) {
        return paymentRepository.findByOrderId(UUID.fromString(input.orderId()))
                .switchIfEmpty(Mono.error(new PaymentNotFoundException("Pagamento não encontrado para o orderId: " + input.orderId())))
                .map(getPaymentMapper::fromPoToOutput);
    }
}