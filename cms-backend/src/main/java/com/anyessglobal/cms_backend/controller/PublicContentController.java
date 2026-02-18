package com.anyessglobal.cms_backend.controller;

import com.anyessglobal.cms_backend.model.ProductCategory;
import com.anyessglobal.cms_backend.model.Product;
import com.anyessglobal.cms_backend.repository.ProductCategoryRepository;
import com.anyessglobal.cms_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
// Allow your local HTML file or deployed site to access this
@CrossOrigin(origins = "*")
public class PublicContentController {

    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // 1. Get All Categories (for the Sidebar)
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> getPublicCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    // 2. Get All Products (for the Grid)
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getPublicProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }
}