package com.anyessglobal.cms_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Unmanaged Switch"

    @Column(length = 500)
    private String shortDescription;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String imageUrl; // need cloudinary url


    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;
}