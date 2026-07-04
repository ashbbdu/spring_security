package com.ecommer_admin.admin_ecommerce.product.controller;

import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.product.dto.CreateProduct;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewProduct;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewProductDto;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductEntity;
import com.ecommer_admin.admin_ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ViewProduct addProduct(@RequestBody CreateProduct createProduct) {
        return productService.addProduct(createProduct);
    }

    @GetMapping("/{productId}")
    public ViewProduct getProductById (@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/update/{productId}")
    public ViewProduct updateProduct (@RequestBody CreateProduct createProduct , @PathVariable Long productId) {
        return productService.updateProduct(createProduct , productId);
    }

    @DeleteMapping("/delete/{productId}")
    public ViewProduct deleteProduct (@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/list")
    public List<ViewProduct> getAllProducts () {
        return productService.getAllProducts();
    }

    @DeleteMapping("/delete-image/{productId}/{productImageId}")
    public ViewProduct deleteProductImage(@PathVariable Long productId , @PathVariable Long productImageId) {
        return productService.deleteProductImage(productId , productImageId);
    }

    @GetMapping("/list1")
    public List<ViewProductDto> getAllProducts1 () {
        return productService.getAllProducts1();
    }

}
