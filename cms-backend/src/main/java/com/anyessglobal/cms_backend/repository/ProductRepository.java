package com.anyessglobal.cms_backend.repository;

import com.anyessglobal.cms_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // A method to find all products by their category's ID
    List<Product> findByCategoryId(Long categoryId);
}
