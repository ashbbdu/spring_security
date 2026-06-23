package com.ecommer_admin.admin_ecommerce.product.entity;

import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import com.ecommer_admin.admin_ecommerce.category.entity.CategoryEntity;
import com.ecommer_admin.admin_ecommerce.inventory.entity.InventoryEntity;
import com.ecommer_admin.admin_ecommerce.order.entity.OrderItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> productImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderItem_id")
    private OrderItemEntity orderItem;

    @OneToOne(mappedBy = "product" , fetch = FetchType.LAZY)
    private InventoryEntity inventory;

}
