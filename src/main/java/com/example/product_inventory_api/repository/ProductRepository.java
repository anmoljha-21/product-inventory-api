package com.example.product_inventory_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_inventory_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
