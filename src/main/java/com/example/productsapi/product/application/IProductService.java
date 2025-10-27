package com.example.productsapi.product.application;

import com.example.productsapi.product.application.dto.request.CreateProductDTORequest;
import com.example.productsapi.product.application.dto.request.UpdateProductDTORequest;
import com.example.productsapi.product.application.dto.response.ProductDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Application service interface that defines the contract for managing {@code Product} entities.
 * <p>
 * This interface belongs to the application layer and abstracts the business operations
 * related to products, decoupling higher-level components (like controllers)
 * from the details of the underlying persistence implementation.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Expose business operations for creating, retrieving, updating, and deleting products.</li>
 *   <li>Return or consume DTO objects to isolate the domain model from external layers.</li>
 *   <li>Provide pagination support for product listings.</li>
 * </ul>
 *
 * <p><b>Implemented by:</b> {@link com.example.productsapi.product.application.ProductService}</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>All methods should validate business rules and handle domain exceptions appropriately.</li>
 *   <li>This interface should not expose persistence details or framework-specific annotations.</li>
 * </ul>
 */
public interface IProductService {

    /**
     * Retrieves a paginated list of all existing products.
     *
     * @param pageable pagination and sorting information, provided automatically by Spring Data.
     * @return a {@link Page} of {@link ProductDTOResponse} objects representing the products.
     *
     * <p><b>Throws:</b> {@link com.example.productsapi.product.application.exception.EmptyProductsListException}
     * if no products are found.</p>
     */
    Page<ProductDTOResponse> getAll(Pageable pageable);

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the unique {@link UUID} of the product to retrieve.
     * @return a {@link ProductDTOResponse} representing the found product.
     *
     * <p><b>Throws:</b> {@link com.example.productsapi.product.application.exception.ProductNotFoundException}
     * if the product does not exist.</p>
     */
    ProductDTOResponse getById(UUID id);

    /**
     * Creates a new product using the provided request data.
     *
     * @param createProductDTORequest the DTO containing the product’s details.
     * @return a {@link ProductDTOResponse} representing the created product.
     *
     * <p><b>Throws:</b> {@link com.example.productsapi.common.exception.InvalidDataEntryException}
     * if input data violates business or persistence rules.</p>
     */
    ProductDTOResponse create(CreateProductDTORequest createProductDTORequest);

    /**
     * Updates an existing product identified by its unique ID.
     *
     * @param id the {@link UUID} of the product to update.
     * @param updateProductDTORequest the DTO containing the updated product data.
     * @return a {@link ProductDTOResponse} representing the updated product.
     *
     * <p><b>Throws:</b></p>
     * <ul>
     *   <li>{@link com.example.productsapi.product.application.exception.ProductNotFoundException}
     *   if the product does not exist.</li>
     *   <li>{@link com.example.productsapi.common.exception.InvalidDataEntryException}
     *   if the updated data violates business constraints.</li>
     * </ul>
     */
    ProductDTOResponse update(UUID id, UpdateProductDTORequest updateProductDTORequest);

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id the {@link UUID} of the product to delete.
     *
     * <p><b>Throws:</b> {@link com.example.productsapi.product.application.exception.ProductNotFoundException}
     * if the product does not exist.</p>
     */
    void delete(UUID id);

}
