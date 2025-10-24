package com.example.productsapi.product.infrastructure.restcontroller;

import com.example.productsapi.product.infrastructure.restcontroller.dto.ProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface IProductController {

    ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable);
    ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id);
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO);
    ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO);
    ResponseEntity<?> deleteProduct(@PathVariable UUID id);

}
