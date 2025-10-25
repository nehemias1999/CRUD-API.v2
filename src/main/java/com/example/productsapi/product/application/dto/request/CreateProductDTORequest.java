package com.example.productsapi.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductDTORequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull @Positive
    private Long stock;
    @NotNull @Positive
    private Double basePrice;
    @NotNull @Positive
    private Double costPrice;

}
