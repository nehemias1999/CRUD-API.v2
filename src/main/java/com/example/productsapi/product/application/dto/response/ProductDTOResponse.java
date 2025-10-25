package com.example.productsapi.product.application.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDTOResponse {

    private UUID id;
    private String name;
    private String description;
    private Long stock;
    private Double basePrice;
    private Double costPrice;

}
