package com.example.productsapi.product.application.dto.mapper;

import com.example.productsapi.product.application.dto.request.CreateProductDTORequest;
import com.example.productsapi.product.application.dto.request.UpdateProductDTORequest;
import com.example.productsapi.product.application.dto.response.ProductDTOResponse;
import com.example.productsapi.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface IProductDTOMapper {

    Product toProduct(CreateProductDTORequest createProductDTORequest);
    Product toProduct(UpdateProductDTORequest updateProductDTORequest);
    ProductDTOResponse toProductDTOResponse(Product product);

}
