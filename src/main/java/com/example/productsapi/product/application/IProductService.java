package com.example.productsapi.product.application;

import com.example.productsapi.product.application.dto.request.CreateProductDTORequest;
import com.example.productsapi.product.application.dto.request.UpdateProductDTORequest;
import com.example.productsapi.product.application.dto.response.ProductDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProductService {

    Page<ProductDTOResponse> getAll(Pageable pageable);
    ProductDTOResponse getById(UUID id);
    ProductDTOResponse create(CreateProductDTORequest createProductDTORequest);
    ProductDTOResponse update(UUID id, UpdateProductDTORequest updateProductDTORequest);
    void delete(UUID id);

}
