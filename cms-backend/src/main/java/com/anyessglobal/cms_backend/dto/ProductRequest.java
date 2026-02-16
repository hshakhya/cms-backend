package com.anyessglobal.cms_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String shortDescription;

    @NotBlank(message = "Full description is required")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    // This is the ID of the category (e.g., 1 for "Connectivity")
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}