package com.app.example.shared.exception;

/**
 * Exceção lançada quando um produto não é encontrado no repositório.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
