package com.ecommer_admin.admin_ecommerce.category.service;

import ch.qos.logback.core.model.Model;
import com.ecommer_admin.admin_ecommerce.category.dto.CreateCategory;
import com.ecommer_admin.admin_ecommerce.category.dto.ViewCategoryDto;
import com.ecommer_admin.admin_ecommerce.category.entity.CategoryEntity;
import com.ecommer_admin.admin_ecommerce.category.repository.CategoryRepository;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public ViewCategoryDto createCategory(CreateCategory createCategory) {
        CategoryEntity category = modelMapper.map(createCategory, CategoryEntity.class);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, ViewCategoryDto.class);
    }

    public ViewCategoryDto getCategoryById(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("User with id " + categoryId + " not found !"));
        return modelMapper.map(category , ViewCategoryDto.class);
    }

    public ViewCategoryDto deleteCategoryById(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("User with id " + categoryId + " not found !"));
        categoryRepository.delete(category);
        return modelMapper.map(category , ViewCategoryDto.class);
    }

    public List<ViewCategoryDto> getAllCategories () {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(res ->
                        modelMapper.map(res , ViewCategoryDto.class)).toList();
    }

    public ViewCategoryDto updateCategory(CreateCategory createCategory , Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("User with id " + categoryId + " not found !"));
        category.setName(createCategory.getName());
        category.setDescription(createCategory.getDescription());
        CategoryEntity savedCategory =  categoryRepository.save(category);
        return modelMapper.map(savedCategory , ViewCategoryDto.class);
    }
}
