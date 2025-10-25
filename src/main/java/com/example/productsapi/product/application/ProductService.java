package com.example.productsapi.product.application;

import com.example.productsapi.common.exception.InvalidDataEntryException;
import com.example.productsapi.product.application.dto.mapper.IProductDTOMapper;
import com.example.productsapi.product.application.dto.request.CreateProductDTORequest;
import com.example.productsapi.product.application.dto.request.UpdateProductDTORequest;
import com.example.productsapi.product.application.dto.response.ProductDTOResponse;
import com.example.productsapi.product.application.exception.EmptyProductsListException;
import com.example.productsapi.product.application.exception.ProductNotFoundException;
import com.example.productsapi.product.domain.Product;
import com.example.productsapi.product.domain.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductDTOMapper productDTOMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTOResponse> getAll(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);

        if(productsPage.isEmpty())
            throw new EmptyProductsListException();

        return productsPage
                .map(productDTOMapper::toProductDTOResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTOResponse getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productDTOMapper.toProductDTOResponse(product);
    }

    @Override
    public ProductDTOResponse create(CreateProductDTORequest createProductDTORequest) {
        try {

            Product product = productDTOMapper.toProduct(createProductDTORequest);

            validateProductData(product);

            Product createdProduct = productRepository.save(product);

            return productDTOMapper.toProductDTOResponse(createdProduct);

        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new InvalidDataEntryException();
        }
    }

    @Override
    public ProductDTOResponse update(UUID id, UpdateProductDTORequest updateProductDTORequest) {
        if(!productRepository.existsById(id))
            throw new ProductNotFoundException();

        try {

            updateProductDTORequest.setId(id);

            Product product = productDTOMapper.toProduct(updateProductDTORequest);

            validateProductData(product);

            Product updatedProduct = productRepository.save(product);

            return productDTOMapper.toProductDTOResponse(updatedProduct);

        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new InvalidDataEntryException();
        }
    }

    @Override
    public void delete(UUID id) {
        if(!productRepository.existsById(id))
            throw new ProductNotFoundException();

        productRepository.deleteById(id);
    }

    private void validateProductData(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new InvalidDataEntryException("Product name is required");
        }

        if (product.getBasePrice() == null || product.getBasePrice() <= 0) {
            throw new InvalidDataEntryException("Base price must be greater than zero");
        }

        if (product.getCostPrice() == null || product.getCostPrice() <= 0) {
            throw new InvalidDataEntryException("Cost price must be greater than zero");
        }

        if (product.getBasePrice() < product.getCostPrice()) {
            throw new InvalidDataEntryException("Base price cannot be lower than cost price");
        }

        if (product.getStock() == null || product.getStock() < 0) {
            throw new InvalidDataEntryException("Stock cannot be negative");
        }
    }

}
