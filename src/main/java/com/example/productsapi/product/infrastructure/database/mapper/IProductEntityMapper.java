package com.example.productsapi.product.infrastructure.database.mapper;

import com.example.productsapi.product.domain.Product;
import com.example.productsapi.product.infrastructure.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface IProductEntityMapper {

    ProductEntity toProductEntity(Product product);
    Product toProduct(ProductEntity productEntity);

}
