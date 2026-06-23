package com.ecommer_admin.admin_ecommerce.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
@NotBlank
public class CreateImageDto {

    @NotBlank(message = "Image URL cannot be empty")
    private String imageUrl;

    @NotNull(message = "Display order cannot be null")
    @Min(value = 0, message = "Display order must be greater than or equal to 0")
    private Integer displayOrder;
}
