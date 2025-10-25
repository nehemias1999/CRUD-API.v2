package com.example.productsapi.product.infrastructure.database;

import com.example.productsapi.product.domain.Product;
import com.example.productsapi.product.domain.repository.IProductRepository;
import com.example.productsapi.product.infrastructure.database.entity.ProductEntity;
import com.example.productsapi.product.infrastructure.database.mapper.IProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements IProductRepository {

    private final IJPAProductRepository jpaProductRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpaProductRepository.findAll(pageable)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaProductRepository.findById(id)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productEntityMapper.toProductEntity(product);
        ProductEntity createdProductEntity = jpaProductRepository.save(productEntity);
        return productEntityMapper.toProduct(createdProductEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaProductRepository.existsById(id);
    }

}
