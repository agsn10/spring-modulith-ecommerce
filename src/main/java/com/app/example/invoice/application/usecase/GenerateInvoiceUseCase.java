package com.app.example.invoice.application.usecase;

import com.app.example.invoice.application.command.GenerateInvoiceCommand;
import com.app.example.invoice.domain.event.InvoiceGenerated;
import com.app.example.invoice.domain.po.InvoicePO;
import com.app.example.invoice.infra.repository.InvoiceRepository;
import com.app.example.invoice.application.usecase.helper.GenerateInvoiceHelper;
import com.app.example.order.application.ppi.OrderPort;
import com.app.example.order.domain.dto.FindByIdDTO;
import com.app.example.shared.usecase.IUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Caso de uso responsável por gerar uma nova fatura (Invoice).
 * <p>
 * Este caso de uso cria uma fatura persistente no banco de dados a partir dos dados fornecidos
 * e publica o evento {@link InvoiceGenerated} após o sucesso da operação.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Qualifier("generateInvoiceUseCase")
public class GenerateInvoiceUseCase implements IUseCase<GenerateInvoiceCommand.Input, Mono<GenerateInvoiceCommand.Output>> {

    private final InvoiceRepository invoiceRepository;
    private final OrderPort orderPort;
    private final ApplicationEventPublisher publisher;

    /**
     * Executa o caso de uso de geração de uma nova fatura.
     * Após persistir a fatura com sucesso, publica o evento {@link InvoiceGenerated}.
     *
     * @param input dados necessários para gerar a fatura
     * @return uma instância de {@link GenerateInvoiceCommand.Output} representando o resultado
     */
    @Override
    public Mono<GenerateInvoiceCommand.Output> execute(GenerateInvoiceCommand.Input input) {
        log.info("Iniciando a geração da fatura para o pedido ID: {}", input.orderId());

        FindByIdDTO.Request request = new FindByIdDTO.Request(UUID.fromString(input.orderId()));

        return orderPort.findById(request)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Pedido não encontrado para o ID: " + input.orderId())))
                .map(order -> {
                    InvoicePO invoice = GenerateInvoiceHelper.buildInvoiceFrom(order.totalAmount(), order.id().toString());
                    log.debug("Fatura construída: {}", invoice);
                    return invoice;
                })
                .flatMap(invoice -> invoiceRepository.save(invoice)
                        .doOnSuccess(saved -> {
                            log.info("Fatura gerada com sucesso. ID: {}, Número da Fatura: {}, Gerada em: {}",
                                    saved.getId(), saved.getInvoiceNumber(), saved.getGeneratedAt());
                            publisher.publishEvent(new InvoiceGenerated(
                                    saved.getId(), saved.getOrderId(), saved.getGeneratedAt()
                            ));
                        })
                        .doOnError(error -> log.error("Erro ao gerar fatura para o pedido ID: {}", input.orderId(), error))
                        .map(saved -> new GenerateInvoiceCommand.Output(
                                saved.getId(),
                                saved.getInvoiceNumber(),
                                saved.getGeneratedAt(),
                                "Fatura gerada com sucesso."
                        ))
                );
    }
}
