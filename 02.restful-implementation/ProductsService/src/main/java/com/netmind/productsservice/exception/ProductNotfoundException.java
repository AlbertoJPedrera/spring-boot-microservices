package com.netmind.productsservice.exception;
public class ProductNotfoundException extends RuntimeException {
    protected static final long serialVersionUID = 1L;

    public ProductNotfoundException() {
        super("Product not found");
    }
    public ProductNotfoundException(Long productId) {
        super("Product with id: " + productId + " not found");
    }
}