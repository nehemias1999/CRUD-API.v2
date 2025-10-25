package com.example.productsapi.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateProductDTORequest {

    private UUID id;
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
