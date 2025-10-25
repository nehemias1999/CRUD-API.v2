package com.example.productsapi.product.infrastructure.database;

import com.example.productsapi.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IJPAProductRepository extends JpaRepository<ProductEntity, UUID> {
}
