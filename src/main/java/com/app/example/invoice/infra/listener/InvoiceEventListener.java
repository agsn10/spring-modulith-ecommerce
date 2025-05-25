package com.app.example.invoice.infra.listener;

import com.app.example.invoice.domain.event.InvoiceGenerated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

/**
 * Listener para eventos relacionados à geração de faturas.
 *
 * Esse listener será acionado somente após a transação da criação da fatura
 * ter sido comitada com sucesso.
 *
 * Responsável por executar lógica de pós-processamento relacionada à geração
 * de faturas, como notificações ou integrações com outros sistemas.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
public class InvoiceEventListener {

    /**
     * Manipula o evento {@link InvoiceGenerated} de forma assíncrona após o commit da transação.
     * <p>
     * Este método será executado em uma thread separada para não impactar a performance da transação principal.
     * Ideal para atividades que não precisam ocorrer de forma síncrona, como:
     * <ul>
     *   <li>Envio de e-mails de confirmação de fatura</li>
     *   <li>Notificações a outros microsserviços</li>
     *   <li>Registro de auditoria ou logs</li>
     * </ul>
     *
     * @param event evento de fatura gerada contendo informações como ID da fatura, ID do pedido e data de geração.
     */
    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleInvoiceGenerated(InvoiceGenerated event) {
        log.info("🧾 Fatura gerada: invoiceId={}, orderId={}, data={}",
                event.invoiceId(), event.orderId(), event.generatedAt());

        // Aqui você pode realizar ações como:
        // - Enviar e-mail de notificação
        // - Publicar evento para outro microserviço
        // - Gravar log de auditoria, etc.
    }
}
