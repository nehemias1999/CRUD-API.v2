package com.example.productsapi.product.infrastructure.restcontroller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDTO {

    private UUID id;
    private String name;
    private String description;
    private Long stock;
    private Double basePrice;
    private Double costPrice;

}
