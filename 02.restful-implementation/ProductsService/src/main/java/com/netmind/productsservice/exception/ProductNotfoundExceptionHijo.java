package com.netmind.productsservice.exception;

public class ProductNotfoundExceptionHijo extends ProductNotfoundException {
    public ProductNotfoundExceptionHijo() {
    }

    public ProductNotfoundExceptionHijo(Long productId) {
        super(productId);
    }
}
