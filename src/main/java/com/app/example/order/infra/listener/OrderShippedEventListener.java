package com.app.example.order.infra.listener;

import com.app.example.order.domain.event.OrderShipped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

/**
 * Listener para eventos relacionados ao envio de pedidos.
 *
 * Esse listener será acionado somente após a transação do envio do pedido
 * ter sido comitada com sucesso.
 *
 * @author <a href="mailto:antonio.neto@example.com">Antonio Neto</a>
 */
@Slf4j
@Component
public class OrderShippedEventListener {

    /**
     * Manipula o evento {@link OrderShipped} após o commit da transação.
     *
     * @param event Evento de pedido enviado.
     */
    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderShipped(OrderShipped event) {
        log.info("📦 Pedido enviado: orderId={}", event.orderId());

        // Aqui você pode realizar ações como:
        // - Enviar e-mail de notificação para o cliente
        // - Publicar evento para outro microserviço
        // - Atualizar status do pedido no sistema, etc.
        log.info("Processamento do evento OrderShipped concluído.");
    }
}