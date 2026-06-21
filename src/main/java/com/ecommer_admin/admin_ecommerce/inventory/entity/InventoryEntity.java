package com.ecommer_admin.admin_ecommerce.inventory.entity;

import com.ecommer_admin.admin_ecommerce.product.entity.ProductEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , length = 255)
    private BigDecimal availableStock;

    @Column
    private BigDecimal minimumStock;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToMany(
            mappedBy = "inventory"
    )
    private List<InventoryTransactionEntity> transactions;

}
