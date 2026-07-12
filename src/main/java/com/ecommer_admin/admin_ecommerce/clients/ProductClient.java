package com.ecommer_admin.admin_ecommerce.clients;

import com.ecommer_admin.admin_ecommerce.product_client.dto.ProductDto;

import java.util.List;



public interface ProductClient {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
}
