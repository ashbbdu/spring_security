package com.ecommer_admin.admin_ecommerce.category.service;

import ch.qos.logback.core.model.Model;
import com.ecommer_admin.admin_ecommerce.category.dto.CreateCategory;
import com.ecommer_admin.admin_ecommerce.category.dto.ViewCategoryDto;
import com.ecommer_admin.admin_ecommerce.category.entity.CategoryEntity;
import com.ecommer_admin.admin_ecommerce.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
