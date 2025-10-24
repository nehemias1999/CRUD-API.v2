package com.example.productsapi.product.infrastructure.restcontroller;

import com.example.productsapi.product.application.IProductService;
import com.example.productsapi.product.domain.Product;
import com.example.productsapi.product.infrastructure.restcontroller.dto.ProductDTO;
import com.example.productsapi.product.infrastructure.restcontroller.mapper.IProductMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController implements IProductController {

    private final IProductService productService;
    private final IProductMapper productMapper;

    public ProductsController(
            IProductService productService,
            IProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
        List<ProductDTO> productsDTOList = productService.getAllProductsPagedAnSorted(pageable)
                .stream()
                .map(productMapper::toProductDTO)
                .toList();

        return new ResponseEntity<>(productsDTOList, HttpStatus.OK);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);

        ProductDTO productDTO = productMapper.toProductDTO(product);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);

        Product createdProduct = productService.createProduct(product);

        ProductDTO responseProductDTO = productMapper.toProductDTO(createdProduct);

        return new ResponseEntity<>(responseProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);

        Product updatedProduct = productService.updateProduct(id, product);

        ProductDTO responseProductDTO = productMapper.toProductDTO(updatedProduct);

        return new ResponseEntity<>(responseProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
