package com.ecommer_admin.admin_ecommerce.product.repository;

import com.ecommer_admin.admin_ecommerce.product.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity , Long> {
    public boolean existsById(Long productImageId);
    public boolean existsByDisplayOrder(Integer displayOrder);
}
