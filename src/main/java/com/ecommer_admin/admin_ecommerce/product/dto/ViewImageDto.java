package com.ecommer_admin.admin_ecommerce.product.dto;

import lombok.Data;

@Data
public class ViewImageDto {
    private Long id;
    private String imageUrl;
    private Integer displayOrder;
}
