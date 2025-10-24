package com.example.productsapi.exceptions;

import com.example.productsapi.product.infrastructure.restcontroller.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(EmptyProductsListException.class)
    public ResponseEntity<?> emptyProductsList(EmptyProductsListException emptyProductsListException) {
        ResponseErrorDTO responseErrorDto = new ResponseErrorDTO(HttpStatus.NOT_FOUND, emptyProductsListException.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFound(ProductNotFoundException productNotFoundException) {
        ResponseErrorDTO responseErrorDto = new ResponseErrorDTO(HttpStatus.NOT_FOUND, productNotFoundException.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataEntryException.class)
    public ResponseEntity<?> invalidDataEntry(InvalidDataEntryException invalidDataEntryException) {
        ResponseErrorDTO responseErrorDto = new ResponseErrorDTO(HttpStatus.BAD_REQUEST, invalidDataEntryException.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
    }

}
