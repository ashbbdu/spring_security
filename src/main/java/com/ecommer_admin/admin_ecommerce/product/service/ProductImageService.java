package com.ecommer_admin.admin_ecommerce.product.service;

import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.product.dto.CreateImageDto;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewImageDto;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductEntity;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductImageEntity;
import com.ecommer_admin.admin_ecommerce.product.repository.ProductImageRepository;
import com.ecommer_admin.admin_ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public ViewImageDto createProductImageAndAssignToProduct(CreateImageDto createImageDto , Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Invalid Product"));

        System.out.println(product.getProductImages());

        ProductImageEntity productImage = modelMapper.map(createImageDto , ProductImageEntity.class);


//        product.setProductImages(List.of(productImage));
//        product.setProduct(new ArrayList<>(List.of(productImage)));
        productImage.setProduct(product);
//        productRepository.save(product);
        ProductImageEntity savedProductImage =  productImageRepository.save(productImage);
        return modelMapper.map(savedProductImage , ViewImageDto.class);
    }
}
