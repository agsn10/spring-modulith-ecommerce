package com.app.example.order.domain.event;

import java.util.UUID;

/**
 * Evento que representa o envio de um pedido (Order).
 * Contém o identificador único do pedido que foi enviado.
 *
 * @param orderId Identificador único do pedido enviado.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public record OrderShipped(UUID orderId) {
}

