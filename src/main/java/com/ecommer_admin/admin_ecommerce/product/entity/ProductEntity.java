package com.ecommer_admin.admin_ecommerce.product.entity;

import com.ecommer_admin.admin_ecommerce.brand.BrandEntity;
import com.ecommer_admin.admin_ecommerce.category.entity.CategoryEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , length = 255)
    private String sku;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal costPrice;

    @Column
    private Boolean status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> productImages;

}
