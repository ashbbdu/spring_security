package com.ecommer_admin.admin_ecommerce.category.controller;

import com.ecommer_admin.admin_ecommerce.category.dto.CreateCategory;
import com.ecommer_admin.admin_ecommerce.category.dto.ViewCategoryDto;
import com.ecommer_admin.admin_ecommerce.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path = "/create")
    public ViewCategoryDto createCategory (@RequestBody @Valid CreateCategory createCategory) {
        return categoryService.createCategory(createCategory);
    }
}
