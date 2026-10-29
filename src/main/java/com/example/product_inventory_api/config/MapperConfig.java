package com.example.product_inventory_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.product_inventory_api.mapper.ProductMapper;
import com.example.product_inventory_api.mapper.ProductMapperImpl;

@Configuration
public class MapperConfig {

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapperImpl();
    }
}