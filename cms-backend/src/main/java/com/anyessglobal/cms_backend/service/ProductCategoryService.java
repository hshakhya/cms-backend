package com.anyessglobal.cms_backend.service;

import com.anyessglobal.cms_backend.dto.ProductCategoryRequest;
import com.anyessglobal.cms_backend.model.ProductCategory;
import com.anyessglobal.cms_backend.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    public List<ProductCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<ProductCategory> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public ProductCategory createCategory(ProductCategoryRequest request) {
        // You could add a check here to see if the slug already exists

        var category = ProductCategory.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .build();

        return categoryRepository.save(category);
    }

    public Optional<ProductCategory> updateCategory(Long id, ProductCategoryRequest request) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(request.getName());
                    existingCategory.setSlug(request.getSlug());
                    return categoryRepository.save(existingCategory);
                });
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {

            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
