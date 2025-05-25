package com.app.example.shared.exception;

/**
 * Exceção lançada quando um pagamento não é encontrado no repositório.
 */
public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}