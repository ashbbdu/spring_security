package com.ecommer_admin.admin_ecommerce.category.repository;

import com.ecommer_admin.admin_ecommerce.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity , Long> {
//    public CategoryEntity findById(Long categoryId);
}
