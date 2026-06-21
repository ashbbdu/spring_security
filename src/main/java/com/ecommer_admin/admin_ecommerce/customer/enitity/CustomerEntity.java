package com.ecommer_admin.admin_ecommerce.customer.enitity;

import com.ecommer_admin.admin_ecommerce.address.entity.AddressEntity;
import com.ecommer_admin.admin_ecommerce.order.entity.OrderEntity;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    private Boolean status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
