package com.app.example.shared.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}