package com.ecommer_admin.admin_ecommerce.category.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCategory {
    @NotNull(message = "Category name should not be empty")
    @NotBlank(message = "Category name should not be empty")
    @Size(min = 4 , max = 255 , message = "Category name should be in between 4 to 255 characters.")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 4 , message = "Category description should be greater than 4 characters.")
    private String description;
    @NotNull(message = "Status should be true or false")
    private Boolean status;
}
