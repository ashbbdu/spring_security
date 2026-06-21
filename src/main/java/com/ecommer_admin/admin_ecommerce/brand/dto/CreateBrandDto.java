package com.ecommer_admin.admin_ecommerce.brand.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBrandDto {
    @NotBlank(message = "Brand name is required")
    @Size(max = 255, message = "Brand name cannot exceed 255 characters")
    private String name;

    private String description;

    private String logoUrl;

    private Boolean status;
}
