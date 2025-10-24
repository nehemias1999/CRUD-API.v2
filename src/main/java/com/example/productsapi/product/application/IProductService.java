package com.example.productsapi.product.application;

import com.example.productsapi.product.domain.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    List<Product> getAllProductsPagedAnSorted(Pageable pageable);
    Product getProductById(UUID id);
    Product createProduct(Product product);
    Product updateProduct(UUID id, Product product);
    void deleteProduct(UUID id);

}
