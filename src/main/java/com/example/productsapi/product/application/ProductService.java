package com.example.productsapi.product.application;

import com.example.productsapi.exceptions.EmptyProductsListException;
import com.example.productsapi.exceptions.InvalidDataEntryException;
import com.example.productsapi.exceptions.ProductNotFoundException;
import com.example.productsapi.product.domain.Product;
import com.example.productsapi.product.domain.IProductRepository;
import com.example.productsapi.product.infrastructure.database.entity.ProductEntity;
import com.example.productsapi.product.infrastructure.database.mapper.IProductEntityMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    public ProductService(
            IProductRepository productRepository,
            IProductEntityMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public List<Product> getAllProductsPagedAnSorted(@NotNull Pageable pageable) {
        Page<ProductEntity> productsPage = productRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                )
        );

        if(productsPage.isEmpty())
            throw new EmptyProductsListException();

        return productsPage
                .map(productEntityMapper::toProduct)
                .toList();
    }

    @Override
    public Product getProductById(@NotNull UUID id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);

        if(productEntityOptional.isEmpty())
            throw new ProductNotFoundException();

        return productEntityMapper.toProduct(productEntityOptional.get());
    }

    @Override
    public Product createProduct(@NotNull Product product) {
        try {
            ProductEntity productEntity = productEntityMapper.toProductEntity(product);

            ProductEntity createdProductEntity = productRepository.save(productEntity);

            return productEntityMapper.toProduct(createdProductEntity);

        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new InvalidDataEntryException();
        }
    }

    @Override
    public Product updateProduct(@NotNull UUID id, @NotNull Product product) {
        if(!productRepository.existsById(id))
            throw new ProductNotFoundException();

        try {

            product.setId(id);

            ProductEntity productEntity = productEntityMapper.toProductEntity(product);

            ProductEntity updatedProductEntity = productRepository.save(productEntity);

            return productEntityMapper.toProduct(updatedProductEntity);

        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new InvalidDataEntryException();
        }
    }

    @Override
    public void deleteProduct(@NotNull UUID id) {
        if(!productRepository.existsById(id))
            throw new ProductNotFoundException();

        productRepository.deleteById(id);
    }

}
