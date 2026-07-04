package com.ecommer_admin.admin_ecommerce.product.repository;

import com.ecommer_admin.admin_ecommerce.product.dto.ViewProductDto;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity , Long> {
    public boolean existsBySku (String sku);

//    @Query("SELECT DISTINCT p FROM ProductEntity p LEFT JOIN FETCH p.productImages LEFT JOIN FETCH p.inventory")
//    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productImages")
//@EntityGraph(attributePaths = {
//        "brand",
//        "category",
//        "productImages",
//        "inventory"
//})
//    public List<ProductEntity> getAllProducts();

//
//
//    @Override
//    @EntityGraph(attributePaths = {
//            "brand",
//            "category",
//            "productImages",
//            "inventory"
//    })
//    List<ProductEntity> findAll();

    @EntityGraph(attributePaths = {
//            "brand",
            "category",
            "productImages",
            "inventory"
    })
    @Query("SELECT p FROM ProductEntity p")
    List<ProductEntity> getAllProducts();



//    @Query("""
//            SELECT new com.ecommer_admin.admin_ecommerce.product.dto.ViewProductDto(p.id , p.sku , p.sku) FROM ProductEntity p""")
//    public List<ViewProductDto> getAllTest();
//
//    @Query("""
//    SELECT new com.ecommer_admin.admin_ecommerce.product.dto.ViewProductDto(
//        p.id,
//        p.sku,
//        c.name,
//        pi.id,
//        pi.imageUrl
//    )
//    FROM ProductEntity p
//    LEFT JOIN p.category c
//    LEFT JOIN p.productImages pi
//    """)
//    List<ViewProductDto> getAllTest();


    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productImages")
    List<ProductEntity> getAllProducts1(Pageable pageable);

    @Query("""
    SELECT p.id
    FROM ProductEntity p
    ORDER BY p.id
    """)
    Page<Long> findProductIds(Pageable pageable);

    @Query("""
    SELECT DISTINCT p
    FROM ProductEntity p
    LEFT JOIN FETCH p.category
    LEFT JOIN FETCH p.productImages
    LEFT JOIN FETCH p.inventory
    WHERE p.id IN :ids
    ORDER BY p.id
    """)
    List<ProductEntity> findAllByIdWithImages(List<Long> ids);
}
//LEFT JOIN FETCH p.inventory