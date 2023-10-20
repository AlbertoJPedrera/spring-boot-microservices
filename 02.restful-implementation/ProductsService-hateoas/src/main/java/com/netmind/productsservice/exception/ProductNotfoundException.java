package com.netmind.productsservice.exception;

public class ProductNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNotfoundException() {
        super("Product not found");
    }
    public ProductNotfoundException(Long productId) {
        super("Product with id: " + productId + " not found");
    }
}
