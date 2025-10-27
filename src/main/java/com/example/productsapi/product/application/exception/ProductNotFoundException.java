package com.example.productsapi.product.application.exception;

/**
 * Exception thrown when a product cannot be found by the given identifier.
 * <p>
 * This exception is part of the application layer and represents
 * a business condition where a requested product does not exist
 * in the persistence layer.
 * </p>
 *
 * <p><b>Typical Scenarios:</b></p>
 * <ul>
 *   <li>Attempting to retrieve a product that does not exist in the database.</li>
 *   <li>Updating or deleting a product using a non-existent ID.</li>
 * </ul>
 *
 * <p><b>HTTP Mapping:</b> Translated to {@code 404 NOT FOUND} by
 * {@link com.example.productsapi.common.exception.infrastructure.controller.ExceptionController}.</p>
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Creates a new {@code ProductNotFoundException} with a default message.
     */
    public ProductNotFoundException() {
        super("Product not found!");
    }

    /**
     * Creates a new {@code ProductNotFoundException} with a custom message.
     *
     * @param message a human-readable description of the error.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

}
