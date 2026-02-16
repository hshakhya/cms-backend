//package com.anyessglobal.cms_backend.controller;
//
//import com.anyessglobal.cms_backend.dto.ProductCategoryRequest;
//import com.anyessglobal.cms_backend.model.ProductCategory;
//import com.anyessglobal.cms_backend.service.ProductCategoryService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/categories") // Base URL for all category endpoints
//@RequiredArgsConstructor
//public class ProductCategoryController {
//
//    private final ProductCategoryService categoryService;
//
//    // POST /api/categories
//    @PostMapping
//    public ResponseEntity<ProductCategory> createCategory(
//            @Valid @RequestBody ProductCategoryRequest request
//    ) {
//        ProductCategory createdCategory = categoryService.createCategory(request);
//        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
//    }
//
//    // GET /api/categories
//    @GetMapping
//    public ResponseEntity<List<ProductCategory>> getAllCategories() {
//        // Note: This endpoint is public for React to fetch.
//        // We need to modify SecurityConfig to allow this.
//        List<ProductCategory> categories = categoryService.getAllCategories();
//        return ResponseEntity.ok(categories);
//    }
//
//    // GET /api/categories/1
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductCategory> getCategoryById(@PathVariable Long id) {
//        return categoryService.getCategoryById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // PUT /api/categories/1
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductCategory> updateCategory(
//            @PathVariable Long id,
//            @Valid @RequestBody ProductCategoryRequest request
//    ) {
//        return categoryService.updateCategory(id, request)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // DELETE /api/categories/1
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
//        if (categoryService.deleteCategory(id)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
package com.anyessglobal.cms_backend.controller;

import com.anyessglobal.cms_backend.dto.ProductCategoryRequest;
import com.anyessglobal.cms_backend.model.ProductCategory;
import com.anyessglobal.cms_backend.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    // POST /api/categories
    @PostMapping
    public ResponseEntity<ProductCategory> createCategory(
            @Valid @RequestBody ProductCategoryRequest request
    ) {
        ProductCategory createdCategory = categoryService.createCategory(request);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // GET /api/categories (SECURED ADMIN ENDPOINT)
    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @GetMapping("/public")
    public ResponseEntity<List<ProductCategory>> getAllPublicCategories() {
        List<ProductCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // GET /api/categories/1
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/categories/1 (SECURED ADMIN ENDPOINT)
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody ProductCategoryRequest request
    ) {
        return categoryService.updateCategory(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/categories/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}