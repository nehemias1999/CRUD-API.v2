package com.example.productsapi.common.exception;

/**
 * Exception thrown when input data violates business rules or persistence constraints.
 * <p>
 * This exception is typically used to indicate that a user-provided input
 * (for example, in a create or update request) is invalid, inconsistent,
 * or fails internal validation within the service layer.
 * </p>
 *
 * <p><b>Typical Scenarios:</b></p>
 * <ul>
 *   <li>Attempting to create a product with a negative price or stock.</li>
 *   <li>Providing invalid data that cannot be persisted due to constraint violations.</li>
 *   <li>Business rule violations detected in the service logic.</li>
 * </ul>
 *
 * <p><b>Handled by:</b> {@link com.example.productsapi.common.exception.infrastructure.controller.ExceptionController}</p>
 */
public class InvalidDataEntryException extends RuntimeException {

    /**
     * Creates a new {@code InvalidDataEntryException} with a default message.
     */
    public InvalidDataEntryException() {
        super("Invalid data entry!");
    }

    /**
     * Creates a new {@code InvalidDataEntryException} with a custom message.
     *
     * @param message a human-readable description of the specific validation error.
     */
    public InvalidDataEntryException(String message) {
        super(message);
    }

}
