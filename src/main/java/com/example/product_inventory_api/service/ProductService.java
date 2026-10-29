
package com.example.product_inventory_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.product_inventory_api.dto.ProductRequest;
import com.example.product_inventory_api.dto.ProductResponse;
import com.example.product_inventory_api.entity.Product;
import com.example.product_inventory_api.repository.ProductRepository;
import com.example.product_inventory_api.mapper.ProductMapper;
import com.example.product_inventory_api.exception.ProductNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return toResponse(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productMapper.updateEntity(request, product);
        Product updatedProduct = productRepository.save(product);

        return toResponse(updatedProduct);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepository.delete(product);
    }

    private ProductResponse toResponse(Product product) {
        return productMapper.toResponse(product);
    }
}
