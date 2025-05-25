package com.app.example.product.infra.listener;

import com.app.example.product.domain.event.StockReduced;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener responsável por tratar o evento {@link StockReduced}.
 *
 * <p>
 * Esse componente é ativado automaticamente pelo Spring quando um evento {@link StockReduced}
 * é publicado após a confirmação (commit) de uma transação.
 * A anotação {@link Async} garante que o processamento será feito de forma assíncrona
 * usando o executor configurado como "eventTaskExecutor".
 * </p>
 *
 * <p>
 * Possíveis ações que podem ser tomadas no processamento:
 * <ul>
 *     <li>Enviar notificações (ex: e-mail, push)</li>
 *     <li>Publicar eventos para outros sistemas ou microserviços</li>
 *     <li>Registrar logs de auditoria</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
@Slf4j
@Component
public class StockReducedEventListener {

    /**
     * Manipula o evento {@link StockReduced} após o commit da transação.
     *
     * @param event o evento que contém informações sobre a redução de estoque
     */
    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(StockReduced event) {
        log.info("Evento StockReduced recebido com sucesso.");
        log.debug("Detalhes do pedido criado: ID={}, Quantity={}", event.productId(), event.quantity());

        // Aqui você pode realizar ações como:
        // - Enviar e-mail de notificação
        // - Publicar evento para outro microserviço
        // - Gravar log de auditoria, etc.

        log.info("Processamento do evento StockReduced concluído.");
    }
}
