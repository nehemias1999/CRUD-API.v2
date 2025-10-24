package com.example.productsapi.product.infrastructure.restcontroller.mapper;

import com.example.productsapi.product.domain.Product;
import com.example.productsapi.product.infrastructure.restcontroller.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface IProductMapper {

    Product toProduct(ProductDTO productDTO);
    ProductDTO toProductDTO(Product product);

}
