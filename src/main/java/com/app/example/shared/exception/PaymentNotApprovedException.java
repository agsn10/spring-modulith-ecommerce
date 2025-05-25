package com.app.example.shared.exception;

public class PaymentNotApprovedException extends RuntimeException {

    public PaymentNotApprovedException(String message) {
        super(message);
    }
}
