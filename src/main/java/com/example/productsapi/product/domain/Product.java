package com.example.productsapi.product.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private UUID id;
    private String name;
    private String description;
    private Long stock;
    private Double basePrice;
    private Double costPrice;

}
