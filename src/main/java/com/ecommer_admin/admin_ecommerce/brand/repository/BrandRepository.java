package com.ecommer_admin.admin_ecommerce.brand.repository;

import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity , Long> {
    public boolean existsByName (String name);
    public boolean existsById (Long id);
//    @Query("""
//       SELECT b
//       FROM BrandEntity b
//       WHERE
//       LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
//       OR
//       LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
//       """)
@Query("""
    SELECT b
    FROM BrandEntity b
    WHERE
        (:name IS NULL OR :name = ''
            OR LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%')))
    AND
        (:description IS NULL OR :description = ''
            OR LOWER(b.description) LIKE LOWER(CONCAT('%', :description, '%')))
""")
    public List<BrandEntity> findAllByNameAndDesc(String name , String description , Pageable pageable);
}
