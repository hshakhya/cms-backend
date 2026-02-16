package com.anyessglobal.cms_backend.service;

import com.anyessglobal.cms_backend.dto.ProductRequest;
import com.anyessglobal.cms_backend.model.Product;
import com.anyessglobal.cms_backend.model.ProductCategory;
import com.anyessglobal.cms_backend.repository.ProductCategoryRepository;
import com.anyessglobal.cms_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get all products for a specific category
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Get one product by its ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Create a new product
    public Product createProduct(ProductRequest request) {
        // 1. Find the category this product belongs to
        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .category(category) // 3. Link the category to the product
                .build();

        return productRepository.save(product);
    }

    // Update an existing product
    public Optional<Product> updateProduct(Long id, ProductRequest request) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // Find the new category if the ID changed
                    ProductCategory category = categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

                    existingProduct.setName(request.getName());
                    existingProduct.setShortDescription(request.getShortDescription());
                    existingProduct.setDescription(request.getDescription());
                    existingProduct.setImageUrl(request.getImageUrl());
                    existingProduct.setCategory(category);

                    return productRepository.save(existingProduct);
                });
    }

    // Delete a product
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}