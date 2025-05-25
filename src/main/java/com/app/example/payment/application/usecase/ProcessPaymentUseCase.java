package com.app.example.payment.application.usecase;

import com.app.example.order.application.ppi.OrderPort;
import com.app.example.order.domain.dto.FindByIdDTO;
import com.app.example.payment.application.command.ProcessPaymentCommand;
import com.app.example.payment.domain.enums.PaymentStatusEnum;
import com.app.example.payment.domain.event.PaymentConfirmed;
import com.app.example.payment.domain.po.PaymentPO;
import com.app.example.payment.infra.repository.PaymentRepository;
import com.app.example.payment.mapper.ProcessPaymentMapper;
import com.app.example.shared.exception.OrderNotFoundException;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso responsável por processar o pagamento de um pedido.
 * <p>
 * Este caso de uso realiza as seguintes etapas:
 * <ul>
 *     <li>Busca os detalhes do pedido pelo ID fornecido;</li>
 *     <li>Valida a existência do pedido, retornando erro se não encontrado;</li>
 *     <li>Cria e persiste o pagamento no repositório;</li>
 *     <li>Publica um evento {@link PaymentConfirmed} após o pagamento ser salvo.</li>
 * </ul>
 *
 * Implementa o contrato {@link IUseCase} com entrada {@link ProcessPaymentCommand.Input}
 * e saída {@link ProcessPaymentCommand.Output}, utilizando reatividade com {@link Mono}.
 *
 * @author
 *     <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("processPaymentUseCase")
public class ProcessPaymentUseCase implements IUseCase<ProcessPaymentCommand.Input, Mono<ProcessPaymentCommand.Output>> {

    /** Porta de acesso ao domínio de pedidos. */
    private final OrderPort orderPort;

    /** Repositório de persistência de pagamentos. */
    private final PaymentRepository paymentRepository;

    /** Mapper para conversão entre entidades e saída do comando. */
    private final ProcessPaymentMapper processPaymentMapper;

    /** Publicador de eventos do Spring. */
    private final ApplicationEventPublisher publisher;

    /**
     * Executa o processamento do pagamento.
     *
     * @param input dados de entrada contendo o ID do pedido e o método de pagamento
     * @return {@link Mono} com os dados de saída do processamento
     * @throws OrderNotFoundException se o pedido não for encontrado
     */
    @Override
    public Mono<ProcessPaymentCommand.Output> execute(ProcessPaymentCommand.Input input) {

        return orderPort.findById(new FindByIdDTO.Request(UUID.fromString(input.orderId())))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Pedido não encontrado com ID: {}", input.orderId());
                    return Mono.error(new OrderNotFoundException(UUID.fromString(input.orderId())));
                }))
                .flatMap(orderResponse -> {
                    PaymentPO paymentPO = new PaymentPO();
                    paymentPO.setMethod(input.method());
                    paymentPO.setAmount(orderResponse.totalAmount());
                    paymentPO.setPaidAt(LocalDateTime.now());
                    paymentPO.setStatus(PaymentStatusEnum.APPROVED.name());

                    return paymentRepository.save(paymentPO)
                            .doOnSuccess(saved -> {
                                // Envia evento após persistência com sucesso
                                publisher.publishEvent(new PaymentConfirmed(saved.getId().toString()));
                                log.info("Evento PaymentConfirmed publicado para pagamento {}", saved.getId().toString());
                            })
                            .map(processPaymentMapper::fromPoToOutput);
                });
    }
}