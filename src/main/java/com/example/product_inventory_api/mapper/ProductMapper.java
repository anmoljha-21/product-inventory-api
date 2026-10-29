package com.example.product_inventory_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.product_inventory_api.dto.ProductRequest;
import com.example.product_inventory_api.dto.ProductResponse;
import com.example.product_inventory_api.entity.Product;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ProductRequest request, @MappingTarget Product product);

    ProductResponse toResponse(Product product);
}