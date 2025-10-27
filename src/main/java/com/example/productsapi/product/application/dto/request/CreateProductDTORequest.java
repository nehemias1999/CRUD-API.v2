package com.example.productsapi.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Data Transfer Object (DTO) used for creating new products.
 * <p>
 * This class encapsulates all the input data required to register a new
 * {@code Product} in the system. It includes validation annotations to ensure
 * that requests contain valid and complete information before reaching the
 * service layer.
 * </p>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>{@code name} and {@code description} must not be blank.</li>
 *   <li>{@code stock}, {@code basePrice}, and {@code costPrice} must be positive and not null.</li>
 * </ul>
 *
 * <p><b>Used by:</b></p>
 * <ul>
 *   <li>{@link com.example.productsapi.product.infrastructure.restcontroller.ProductsController#create(CreateProductDTORequest)}</li>
 *   <li>{@link com.example.productsapi.product.application.IProductService#create(CreateProductDTORequest)}</li>
 * </ul>
 *
 * <p><b>Design Notes:</b></p>
 * <ul>
 *   <li>Acts as a boundary object between the API layer and the application layer.</li>
 *   <li>Ensures that invalid or incomplete data never reaches the domain layer.</li>
 * </ul>
 */
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
