package com.example.productsapi.product.domain.repository;

import com.example.productsapi.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface IProductRepository {

    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(UUID id);
    Product save(Product product);
    void deleteById(UUID id);
    boolean existsById(UUID id);

}
