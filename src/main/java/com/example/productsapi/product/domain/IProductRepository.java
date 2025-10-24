package com.example.productsapi.product.domain;

import com.example.productsapi.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {
}
