package com.example.productsapi.product.infrastructure.restcontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseErrorDTO {

    private HttpStatus statusCode;
    private String errorMessage;

}