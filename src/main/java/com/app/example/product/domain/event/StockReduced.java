package com.app.example.product.domain.event;

public record StockReduced(String productId, int quantity) {
}
