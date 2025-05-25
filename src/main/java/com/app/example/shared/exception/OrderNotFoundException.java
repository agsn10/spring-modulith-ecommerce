package com.app.example.shared.exception;

import java.util.UUID;

/**
 * Exceção lançada quando um pedido não é encontrado pelo ID informado.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(UUID orderId) {
        super("Pedido não encontrado com ID: " + orderId.toString());
    }
}