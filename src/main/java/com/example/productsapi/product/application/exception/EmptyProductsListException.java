package com.example.productsapi.product.application.exception;

/**
 * Exception thrown when the product repository returns an empty list of products.
 * <p>
 * This exception is used to signal that no products were found when attempting
 * to list all available products. It allows the API to respond meaningfully
 * instead of returning an empty payload.
 * </p>
 *
 * <p><b>Typical Scenarios:</b></p>
 * <ul>
 *   <li>Invoking {@code getAll()} on the service when the database has no products.</li>
 *   <li>Used to trigger an HTTP {@code 204 No Content} or a {@code 404 Not Found} response, depending on API design.</li>
 * </ul>
 *
 * <p><b>Handled by:</b> {@link com.example.productsapi.common.exception.infrastructure.controller.ExceptionController}</p>
 */
public class EmptyProductsListException extends RuntimeException {

    /**
     * Creates a new {@code EmptyProductsListException} with a default message.
     */
    public EmptyProductsListException() {
        super("Products list is empty!");
    }

    /**
     * Creates a new {@code EmptyProductsListException} with a custom message.
     *
     * @param message a human-readable description of the condition.
     */
    public EmptyProductsListException(String message) {
        super(message);
    }

}
