package com.app.example.order.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Evento de domínio que representa a criação do pedido.
 * Esse evento é publicado após a pedido ser criada com sucesso.
 *
 * @author <a href="mailto:agsn10@hotmail.com">Antonio Neto</a>
 */
public record OrderCreated(UUID clientId, UUID orderId, BigDecimal totalAmount)
{}

