package com.ecommer_admin.admin_ecommerce.category.controller;

import com.ecommer_admin.admin_ecommerce.category.dto.CreateCategory;
import com.ecommer_admin.admin_ecommerce.category.dto.ViewCategoryDto;
import com.ecommer_admin.admin_ecommerce.category.entity.CategoryEntity;
import com.ecommer_admin.admin_ecommerce.category.repository.CategoryRepository;
import com.ecommer_admin.admin_ecommerce.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path = "/create")
    public ViewCategoryDto createCategory (@RequestBody @Valid CreateCategory createCategory) {
        return categoryService.createCategory(createCategory);
    }

    @PutMapping(path = "/update/{categoryId}")
    public ViewCategoryDto updateCategory (@RequestBody @Valid CreateCategory createCategory , @PathVariable Long categoryId) {
        return categoryService.updateCategory(createCategory , categoryId);
    }

    @GetMapping(path = "/{categoryId}")
    public ViewCategoryDto getCategoryById (@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @DeleteMapping(path = "/{categoryId}")
    public ViewCategoryDto deleteCategoryById (@PathVariable Long categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }

    @GetMapping(path = "/list")
    public List<ViewCategoryDto> getAllCategories () {
        return categoryService.getAllCategories();
    }

}
