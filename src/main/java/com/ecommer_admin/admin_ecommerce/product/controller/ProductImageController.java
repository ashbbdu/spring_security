package com.ecommer_admin.admin_ecommerce.product.controller;

import com.ecommer_admin.admin_ecommerce.product.dto.CreateImageDto;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewImageDto;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewProduct;
import com.ecommer_admin.admin_ecommerce.product.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product-images")
@RequiredArgsConstructor
@Validated
public class ProductImageController {
    private final ProductImageService productImageService;
    @PostMapping(path = "/add-product-image/{productId}")
    public ViewImageDto createProductImage (@RequestBody CreateImageDto createImageDto , @PathVariable Long productId) {
        return productImageService.createProductImageAndAssignToProduct(createImageDto ,productId);
    }

}
