package com.app.example.payment.infra.listerner;

import com.app.example.payment.domain.event.PaymentConfirmed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener responsável por reagir ao evento {@link PaymentConfirmed} emitido após a confirmação de pagamento.
 *
 * <p>Este listener é executado de forma assíncrona após a transação ser confirmada (AFTER_COMMIT),
 * garantindo que o pagamento foi persistido com sucesso antes de iniciar qualquer ação dependente.</p>
 *
 * <p>Exemplos de ações que podem ser executadas:
 * <ul>
 *   <li>Notificação por e-mail</li>
 *   <li>Publicação de evento para outro microserviço</li>
 *   <li>Registro de auditoria</li>
 * </ul>
 * </p>
 *
 * @author Você
 */
@Slf4j
@Component
public class PaymentConfirmedEventListener {

    /**
     * Manipula o evento {@link PaymentConfirmed} após a confirmação da transação.
     *
     * @param event evento que representa a confirmação de um pagamento
     */
    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(PaymentConfirmed event) {
        log.info("Evento PaymentConfirmed recebido com sucesso para pagamento ID: {}", event.paymentId());

        // Aqui você pode realizar ações como:
        // - Enviar e-mail de notificação
        // - Publicar evento para outro microserviço
        // - Gravar log de auditoria, etc.

        // Exemplo fictício:
        log.debug("Iniciando tarefas relacionadas ao pagamento ID: {}", event.paymentId());

        // Simulação de processamento
        try {
            // executar ações reais aqui
            log.info("Processando lógica relacionada ao pagamento confirmado...");
            Thread.sleep(200); // apenas simulação
        } catch (InterruptedException e) {
            log.warn("Interrupção ao processar evento PaymentConfirmed para ID: {}", event.paymentId(), e);
            Thread.currentThread().interrupt();
        }

        log.info("Processamento do evento PaymentConfirmed concluído para pagamento ID: {}", event.paymentId());
    }
}
