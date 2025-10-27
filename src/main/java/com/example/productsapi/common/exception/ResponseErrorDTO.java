package com.example.productsapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object (DTO) used to represent standardized error responses
 * returned by the API when an exception is handled globally.
 * <p>
 * This class provides a structured format for all error messages, ensuring
 * consistency across different endpoints and types of exceptions.
 * </p>
 *
 * <p><b>Typical JSON Response Example:</b></p>
 * <pre>
 * {
 *   "error": "Invalid input data",
 *   "message": "Validation failed",
 *   "fields": {
 *     "name": "must not be blank",
 *     "price": "must be greater than zero"
 *   }
 * }
 * </pre>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *   <li>Returned by {@link com.example.productsapi.common.exception.infrastructure.controller.ExceptionController}</li>
 *   <li>Used for representing field validation errors, missing resources, or unexpected exceptions.</li>
 * </ul>
 *
 * <p><b>Design Notes:</b></p>
 * <ul>
 *   <li>Includes both a general error summary and detailed field-level messages.</li>
 *   <li>Utilizes Lombok annotations to reduce boilerplate (getters, setters, constructors).</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorDTO {

    /**
     * A short, human-readable description of the error category.
     * <p>Example: {@code "Invalid input data"}, {@code "Product not found"}, {@code "Unexpected error"}</p>
     */
    private String error;

    /**
     * A more detailed explanation of the specific error that occurred.
     * <p>Example: {@code "Validation failed for one or more fields"}.</p>
     */
    private String message;

    /**
     * A map containing specific fields and their corresponding validation messages.
     * <p>
     * This is typically used when input validation fails, allowing the client
     * to identify which field caused the issue and why.
     * </p>
     * <p>Example:</p>
     * <pre>
     * {
     *   "name": "must not be blank",
     *   "price": "must be positive"
     * }
     * </pre>
     */
    private Map<String, String> fields;

}
