package com.app.example.order.infra.listener;

import com.app.example.order.domain.event.OrderCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Componente responsável por escutar e reagir a eventos relacionados a pedidos (orders).
 * <p>
 * Utiliza a anotação {@link TransactionalEventListener} para executar lógica de forma
 * reativa a eventos publicados durante o ciclo de vida da transação.
 */
@Slf4j
@Component
public class OrderCreatedEventListener {

    /**
     * Manipula o evento {@link OrderCreated} de forma assíncrona após o commit da transação.
     * <p>
     * Este método será executado em uma thread separada, permitindo que tarefas que não
     * impactam diretamente a transação (como notificações, auditorias ou publicação de eventos)
     * sejam tratadas sem bloquear o fluxo principal da aplicação.
     *
     * @param event evento de criação de pedido contendo informações como ID do pedido,
     *              ID do cliente e valor total da compra.
     */
    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreated event) {
        log.info("Evento OrderCreated recebido com sucesso.");
        log.debug("Detalhes do pedido criado: ID={}, Cliente={}, Valor Total={}",
                event.orderId(), event.clientId(), event.totalAmount());

        // Aqui você pode realizar ações como:
        // - Enviar e-mail de notificação
        // - Publicar evento para outro microserviço
        // - Gravar log de auditoria, etc.

        log.info("Processamento do evento OrderCreated concluído.");
    }
}
