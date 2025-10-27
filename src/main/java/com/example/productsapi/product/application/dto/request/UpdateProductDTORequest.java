package com.example.productsapi.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) used for updating existing products.
 * <p>
 * This class contains the data required to update an existing {@code Product}.
 * It includes both the unique product identifier and the modifiable fields.
 * </p>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>{@code name} and {@code description} must not be blank.</li>
 *   <li>{@code stock}, {@code basePrice}, and {@code costPrice} must be positive and not null.</li>
 *   <li>{@code id} is optional and can be set programmatically by the service layer.</li>
 * </ul>
 *
 * <p><b>Used by:</b></p>
 * <ul>
 *   <li>{@link com.example.productsapi.product.infrastructure.restcontroller.ProductsController#update(UUID, UpdateProductDTORequest)}</li>
 *   <li>{@link com.example.productsapi.product.application.IProductService#update(UUID, UpdateProductDTORequest)}</li>
 * </ul>
 *
 * <p><b>Design Notes:</b></p>
 * <ul>
 *   <li>The {@code id} field is not required in client requests, since it is extracted from the request path.</li>
 *   <li>Validation ensures data integrity before updating the entity in the persistence layer.</li>
 * </ul>
 */
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
