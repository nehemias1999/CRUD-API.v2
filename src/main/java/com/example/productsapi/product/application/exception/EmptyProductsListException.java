package com.example.productsapi.product.application.exception;

public class EmptyProductsListException extends RuntimeException {

    public EmptyProductsListException() {
        super("Products list is empty!");
    }

}
