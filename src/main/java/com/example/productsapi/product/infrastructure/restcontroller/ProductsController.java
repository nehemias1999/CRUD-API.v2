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

@RestController
@RequestMapping("/api/v1/products")
@Validated
@RequiredArgsConstructor
public class ProductsController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTOResponse>> getAll(
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
            Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTOResponse> getById(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTOResponse> create(@Valid @RequestBody CreateProductDTORequest createProductDTORequest) {
        ProductDTOResponse productDTOResponse = productService.create(createProductDTORequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTOResponse> update(
            @PathVariable @NotNull UUID id,
            @Valid @RequestBody UpdateProductDTORequest updateProductDTORequest) {
        return ResponseEntity.ok(productService.update(id, updateProductDTORequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
