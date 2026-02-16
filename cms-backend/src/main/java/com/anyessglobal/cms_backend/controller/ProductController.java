//package com.anyessglobal.cms_backend.controller;
//
//import com.anyessglobal.cms_backend.dto.ProductRequest;
//import com.anyessglobal.cms_backend.model.Product;
//import com.anyessglobal.cms_backend.service.ProductService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/products") // Base URL for all product endpoints
//@RequiredArgsConstructor
//public class ProductController {
//
//    private final ProductService productService;
//
//    // POST /api/products
//    @PostMapping
//    public ResponseEntity<Product> createProduct(
//            @Valid @RequestBody ProductRequest request
//    ) {
//        Product createdProduct = productService.createProduct(request);
//        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
//    }
//
//    // GET /api/products
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts(
//            // Optional filter: /api/products?categoryId=1
//            @RequestParam(required = false) Long categoryId
//    ) {
//        List<Product> products;
//        if (categoryId != null) {
//            products = productService.getProductsByCategoryId(categoryId);
//        } else {
//            products = productService.getAllProducts();
//        }
//        return ResponseEntity.ok(products);
//    }
//
//    // GET /api/products/1
//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        return productService.getProductById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // PUT /api/products/1
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(
//            @PathVariable Long id,
//            @Valid @RequestBody ProductRequest request
//    ) {
//        return productService.updateProduct(id, request)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // DELETE /api/products/1
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        if (productService.deleteProduct(id)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
package com.anyessglobal.cms_backend.controller;

import com.anyessglobal.cms_backend.dto.ProductRequest;
import com.anyessglobal.cms_backend.model.Product;
import com.anyessglobal.cms_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // POST /api/products (SECURED ADMIN ENDPOINT)
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody ProductRequest request
    ) {
        Product createdProduct = productService.createProduct(request);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // GET /api/products (SECURED ADMIN ENDPOINT)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            // Optional filter: /api/products?categoryId=1
            @RequestParam(required = false) Long categoryId
    ) {
        List<Product> products;
        if (categoryId != null) {
            products = productService.getProductsByCategoryId(categoryId);
        } else {
            products = productService.getAllProducts();
        }
        return ResponseEntity.ok(products);
    }

    // 🚨 NEW PUBLIC ENDPOINT: GET /api/public/products 🚨
    @GetMapping("/public")
    public ResponseEntity<List<Product>> getAllPublicProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // GET /api/products/1 (SECURED ADMIN ENDPOINT)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/products/1 (SECURED ADMIN ENDPOINT)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.updateProduct(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/products/1 (SECURED ADMIN ENDPOINT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}