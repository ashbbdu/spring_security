package com.ecommer_admin.admin_ecommerce.product_client.controller;

import com.ecommer_admin.admin_ecommerce.clients.ProductClient;
import com.ecommer_admin.admin_ecommerce.common.advice.ApiResponse;
import com.ecommer_admin.admin_ecommerce.product_client.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-client")
@RequiredArgsConstructor
public class ProductClientController {

    private final ProductClient productClient;

    @GetMapping(path = "/products")
    public List<ProductDto> getAllProductsFromClient () {
        return productClient.getAllProducts();

    }

    @GetMapping(path = "/{id}")
    public ProductDto getProductById (@PathVariable Long id) {
        return productClient.getProductById(id);
    }

}
