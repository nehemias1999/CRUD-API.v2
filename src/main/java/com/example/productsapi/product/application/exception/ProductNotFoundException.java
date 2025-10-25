package com.example.productsapi.product.application.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product not found!");
    }

}
