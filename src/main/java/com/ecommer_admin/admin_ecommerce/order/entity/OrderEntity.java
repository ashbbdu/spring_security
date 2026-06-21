package com.ecommer_admin.admin_ecommerce.order.entity;

import com.ecommer_admin.admin_ecommerce.customer.enitity.CustomerEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String orderNumber;

    @Column(nullable = false, length = 50)
    private String status;
//    PENDING,
//    CONFIRMED,
//    PROCESSING,
//    SHIPPED,
//    DELIVERED,
//    CANCELLED,
//    RETURNED

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Column
    private LocalDateTime deliveredAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems;

}
