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

/**
 * Infrastructure adapter that implements {@link IProductRepository}
 * to interact with the persistence layer using JPA.
 * <p>
 * This class serves as the bridge between the domain layer and the
 * database infrastructure, ensuring the domain model remains isolated
 * from persistence concerns.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Delegates persistence operations to the {@link IJPAProductRepository} (Spring Data JPA).</li>
 *   <li>Maps between domain models ({@link Product}) and JPA entities ({@link ProductEntity}).</li>
 *   <li>Provides CRUD functionality abstracted from database implementation details.</li>
 * </ul>
 *
 * <p><b>Design note:</b> Annotated with {@link Repository} so that Spring can
 * detect it as a persistence component and apply exception translation automatically.</p>
 */
@Repository
@RequiredArgsConstructor
public class ProductRepository implements IProductRepository {

    private final IJPAProductRepository jpaProductRepository;
    private final IProductEntityMapper productEntityMapper;


    /**
     * Retrieves a paginated list of products from the database.
     *
     * @param pageable the pagination and sorting configuration.
     * @return a {@link Page} of {@link Product} domain objects.
     *
     * <p>Uses the mapper to convert each {@link ProductEntity}
     * returned by the JPA repository into its domain representation.</p>
     */
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpaProductRepository.findAll(pageable)
                .map(productEntityMapper::toProduct);
    }

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the UUID of the product to search for.
     * @return an {@link Optional} containing the {@link Product} if found,
     *         or empty if not found.
     *
     * <p>Maps the {@link ProductEntity} result from the JPA repository
     * to a {@link Product} domain object.</p>
     */
    @Override
    public Optional<Product> findById(UUID id) {
        return jpaProductRepository.findById(id)
                .map(productEntityMapper::toProduct);
    }

    /**
     * Persists a new or existing product in the database.
     *
     * @param product the {@link Product} domain object to save.
     * @return the persisted {@link Product} domain object with generated ID or updated fields.
     *
     * <p>Performs a two-way mapping:
     * <ul>
     *   <li>Converts {@link Product} to {@link ProductEntity} before saving.</li>
     *   <li>Converts the saved {@link ProductEntity} back to {@link Product} after persistence.</li>
     * </ul>
     * </p>
     */
    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productEntityMapper.toProductEntity(product);
        ProductEntity createdProductEntity = jpaProductRepository.save(productEntity);
        return productEntityMapper.toProduct(createdProductEntity);
    }

    /**
     * Deletes a product from the database by its UUID.
     *
     * @param id the UUID of the product to delete.
     *
     * <p>Delegates the operation directly to the JPA repository.</p>
     */
    @Override
    public void deleteById(UUID id) {
        jpaProductRepository.deleteById(id);
    }

    /**
     * Checks whether a product with the specified UUID exists.
     *
     * @param id the UUID of the product to check.
     * @return {@code true} if a product exists with that ID, {@code false} otherwise.
     *
     * <p>Useful for validation before performing update or delete operations.</p>
     */
    @Override
    public boolean existsById(UUID id) {
        return jpaProductRepository.existsById(id);
    }

}
