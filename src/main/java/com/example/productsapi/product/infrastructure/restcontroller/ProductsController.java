package com.example.productsapi.product.infrastructure.restcontroller;

import com.example.productsapi.product.application.IProductService;
import com.example.productsapi.product.application.dto.request.CreateProductDTORequest;
import com.example.productsapi.product.application.dto.request.UpdateProductDTORequest;
import com.example.productsapi.product.application.dto.response.ProductDTOResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller that handles HTTP operations related to {@link com.example.productsapi.product.domain.Product}.
 * <p>
 * Provides CRUD endpoints to manage products within the system.
 * Implements pagination, sorting, and validation on inputs.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/products")
@Validated
@RequiredArgsConstructor
public class ProductsController {

    private final IProductService productService;

    /**
     * Retrieves a paginated and sorted list of all products.
     *
     * @param pageable the pagination and sorting configuration.
     *                 Defaults to page size 20 and sorted ascending by ID.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link ProductDTOResponse}.
     *
     * @response 200 Successfully retrieved the paginated list of products.
     * @response 404 If there are no products available (handled by {@code EmptyProductsListException}).
     */
    @GetMapping
    public ResponseEntity<Page<ProductDTOResponse>> getAll(
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
            Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    /**
     * Retrieves a single product by its unique identifier.
     *
     * @param id the UUID of the product to retrieve (must not be null).
     * @return a {@link ResponseEntity} containing the {@link ProductDTOResponse}.
     *
     * @response 200 Successfully found and returned the product.
     * @response 400 If the provided UUID format is invalid.
     * @response 404 If no product is found with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTOResponse> getById(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    /**
     * Creates a new product in the system.
     *
     * @param createProductDTORequest the request body containing product details.
     *                                Must be valid according to {@link CreateProductDTORequest} constraints.
     * @return a {@link ResponseEntity} containing the created {@link ProductDTOResponse}.
     *
     * @response 201 Successfully created a new product.
     * @response 400 If request data violates validation rules (e.g., missing or invalid fields).
     * @response 409 If there’s a database constraint violation (e.g., duplicate keys).
     */
    @PostMapping
    public ResponseEntity<ProductDTOResponse> create(@Valid @RequestBody CreateProductDTORequest createProductDTORequest) {
        ProductDTOResponse productDTOResponse = productService.create(createProductDTORequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDTOResponse);
    }

    /**
     * Updates an existing product by its UUID.
     *
     * @param id                      the UUID of the product to update (must not be null).
     * @param updateProductDTORequest the request body containing the new values for the product.
     *                                Must be valid according to {@link UpdateProductDTORequest} constraints.
     * @return a {@link ResponseEntity} containing the updated {@link ProductDTOResponse}.
     *
     * @response 200 Successfully updated the product.
     * @response 400 If validation fails or data is invalid.
     * @response 404 If the product to update is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTOResponse> update(
            @PathVariable @NotNull UUID id,
            @Valid @RequestBody UpdateProductDTORequest updateProductDTORequest) {
        return ResponseEntity.ok(productService.update(id, updateProductDTORequest));
    }

    /**
     * Deletes an existing product by its UUID.
     *
     * @param id the UUID of the product to delete (must not be null).
     * @return a {@link ResponseEntity} with HTTP 204 (No Content) if deletion is successful.
     *
     * @response 204 Successfully deleted the product.
     * @response 400 If the UUID format is invalid.
     * @response 404 If no product is found with the given ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
