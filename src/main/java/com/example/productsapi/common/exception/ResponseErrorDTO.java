package com.example.productsapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorDTO {

    private String error;
    private String message;
    private Map<String, String> fields;

    public ResponseErrorDTO(String error, String message) {
        this.error = error;
        this.message = message;
    }

}
