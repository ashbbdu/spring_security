package com.ecommer_admin.admin_ecommerce.brand.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewBrandDto {
    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
