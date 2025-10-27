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

/**
 * Application service that implements the product-related use cases.
 * <p>
 * Acts as the orchestrator between the domain layer and the infrastructure layer.
 * It applies business rules, performs validations, handles domain exceptions, and manages transactions.
 * </p>
 *
 * <p><b>Transaction management:</b> Annotated with {@link Transactional} at the class level,
 * ensuring all methods run within a transactional context.</p>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductDTOMapper productDTOMapper;

    /**
     * Retrieves a paginated list of products.
     *
     * @param pageable the pagination configuration (page size, number, and sorting).
     * @return a paginated list of products as {@link ProductDTOResponse}.
     *
     * @throws EmptyProductsListException if no products are found in the repository.
     *
     * @Transactional(readOnly = true) to avoid locking and improve performance on read-only operations.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTOResponse> getAll(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);

        if(productsPage.isEmpty())
            throw new EmptyProductsListException();

        return productsPage
                .map(productDTOMapper::toProductDTOResponse);
    }

    /**
     * Retrieves a single product by its unique identifier.
     *
     * @param id the UUID of the product to retrieve.
     * @return the found product as a {@link ProductDTOResponse}.
     *
     * @throws ProductNotFoundException if no product exists with the given UUID.
     *
     * @Transactional(readOnly = true) to ensure data integrity without writing locks.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDTOResponse getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productDTOMapper.toProductDTOResponse(product);
    }

    /**
     * Creates a new product in the system.
     *
     * @param createProductDTORequest DTO containing the details of the product to be created.
     * @return the created product as a {@link ProductDTOResponse}.
     *
     * @throws InvalidDataEntryException if validation fails or database constraints are violated.
     * @throws DataIntegrityViolationException if the database rejects the insert due to constraint violations.
     * @throws JpaSystemException or PersistenceException for JPA-level errors.
     *
     * <p>Transactional method: Rolls back the transaction on any unchecked exception.</p>
     */
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

    /**
     * Updates an existing product identified by its UUID.
     *
     * @param id the UUID of the product to update.
     * @param updateProductDTORequest DTO containing the new data for the product.
     * @return the updated product as a {@link ProductDTOResponse}.
     *
     * @throws ProductNotFoundException if no product exists with the provided UUID.
     * @throws InvalidDataEntryException if input data is invalid or violates business rules.
     * @throws DataIntegrityViolationException if database constraints are violated.
     * @throws JpaSystemException or PersistenceException for JPA-level errors.
     */
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

    /**
     * Deletes a product by its UUID.
     *
     * @param id the UUID of the product to delete.
     *
     * @throws ProductNotFoundException if no product exists with the given UUID.
     *
     * <p>Transactional method: the delete operation is performed within a transaction to ensure consistency.</p>
     */
    @Override
    public void delete(UUID id) {
        if(!productRepository.existsById(id))
            throw new ProductNotFoundException();

        productRepository.deleteById(id);
    }


    /**
     * Validates the integrity and business rules of a {@link Product} before persistence.
     * <p>
     * This method ensures that:
     * <ul>
     *   <li>Product name is not null or blank.</li>
     *   <li>Base price and cost price are positive values.</li>
     *   <li>Base price is greater than or equal to cost price.</li>
     *   <li>Stock is non-negative.</li>
     * </ul>
     * </p>
     *
     * @param product the {@link Product} to validate.
     * @throws InvalidDataEntryException if any validation rule is violated.
     *
     * <p>This is a private utility method, not transactional by itself.</p>
     */
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
