package com.example.productsapi.common.exception.infrastructure.controller;

import com.example.productsapi.common.exception.InvalidDataEntryException;
import com.example.productsapi.common.exception.ResponseErrorDTO;
import com.example.productsapi.product.application.exception.ProductNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST controllers.
 * <p>
 * This class centralizes exception handling across the entire application,
 * ensuring consistent error responses in JSON format.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Captures and handles validation, persistence, and general runtime exceptions.</li>
 *   <li>Builds structured error responses using {@link ResponseErrorDTO}.</li>
 *   <li>Ensures correct HTTP status codes for each error scenario.</li>
 * </ul>
 *
 * <p><b>Design note:</b> Annotated with {@link RestControllerAdvice} so Spring
 * automatically applies this handler to all REST controllers in the application.</p>
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * Handles validation errors thrown when request bodies fail
     * to satisfy constraints defined with {@code @Valid}.
     *
     * @param ex the {@link MethodArgumentNotValidException} containing details of validation errors.
     * @return a {@link ResponseEntity} with HTTP 400 (Bad Request)
     *         and a detailed {@link ResponseErrorDTO} listing invalid fields.
     *
     * <p>This captures validation failures on DTOs annotated with Jakarta Validation constraints.</p>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> onInvalidBody(MethodArgumentNotValidException ex) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage, (a, b)->a));
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", "Validation failed", fields));
    }

    /**
     * Handles constraint violations that occur when query parameters or path variables
     * fail validation constraints (e.g., {@code @NotNull}, {@code @Positive}).
     *
     * @param ex the {@link ConstraintViolationException} containing the violated constraints.
     * @return a {@link ResponseEntity} with HTTP 400 (Bad Request)
     *         and details of each violated constraint.
     *
     * <p>This typically handles validation issues outside the request body, such as query params.</p>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseErrorDTO> onConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> fields = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (a,b)->a));
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", "Validation failed", fields));
    }

    /**
     * Handles cases where a request parameter cannot be converted to the expected type.
     * <p>For example, attempting to convert a non-UUID string into a {@link java.util.UUID}.</p>
     *
     * @param ex the {@link MethodArgumentTypeMismatchException} thrown during type conversion.
     * @return a {@link ResponseEntity} with HTTP 400 (Bad Request) and a descriptive error message.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseErrorDTO> onTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String message = "Invalid value for '" + field + "'";
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", message, Map.of(field, "invalid format")));
    }

    /**
     * Handles cases where a requested product does not exist in the system.
     *
     * @param ex the {@link ProductNotFoundException} thrown when the product is missing.
     * @return a {@link ResponseEntity} with HTTP 404 (Not Found)
     *         and an error message describing the missing resource.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> onNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseErrorDTO("Product not found", ex.getMessage(), null));
    }

    /**
     * Handles invalid input data that violates business rules or persistence constraints.
     *
     * @param ex the {@link InvalidDataEntryException} thrown during product creation or update.
     * @return a {@link ResponseEntity} with HTTP 400 (Bad Request) and an appropriate error message.
     */
    @ExceptionHandler(InvalidDataEntryException.class)
    public ResponseEntity<ResponseErrorDTO> onInvalidData(InvalidDataEntryException ex) {
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", ex.getMessage(), null));
    }

    /**
     * Handles any unanticipated exceptions not explicitly covered by other handlers.
     *
     * @param ex the unexpected {@link Exception}.
     * @return a {@link ResponseEntity} with HTTP 500 (Internal Server Error)
     *         and a general error message.
     *
     * <p>This ensures the client receives a structured JSON response even for unknown errors.</p>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> onGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseErrorDTO("Unexpected error", ex.getMessage(), null));
    }

}
