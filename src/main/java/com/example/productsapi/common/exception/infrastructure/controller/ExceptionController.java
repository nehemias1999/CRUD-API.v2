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

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> onInvalidBody(MethodArgumentNotValidException ex) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage, (a, b)->a));
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", "Validation failed", fields));
    }

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseErrorDTO> onTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String message = "Invalid value for '" + field + "'";
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", message, Map.of(field, "invalid format")));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> onNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseErrorDTO("Product not found", ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidDataEntryException.class)
    public ResponseEntity<ResponseErrorDTO> onInvalidData(InvalidDataEntryException ex) {
        return ResponseEntity.badRequest()
                .body(new ResponseErrorDTO("Invalid input data", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> onGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseErrorDTO("Unexpected error", ex.getMessage(), null));
    }
}
