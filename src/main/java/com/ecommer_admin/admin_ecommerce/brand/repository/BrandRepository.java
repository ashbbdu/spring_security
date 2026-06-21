package com.ecommer_admin.admin_ecommerce.brand.repository;

import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity , Long> {
    public boolean existsByName (String name);
}
