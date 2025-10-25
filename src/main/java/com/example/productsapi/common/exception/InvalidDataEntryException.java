package com.example.productsapi.common.exception;

public class InvalidDataEntryException extends RuntimeException {

    public InvalidDataEntryException() {
        super("Invalid data entry!");
    }

    public InvalidDataEntryException(String message) {
        super(message);
    }

}
