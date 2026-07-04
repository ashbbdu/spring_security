package com.ecommer_admin.admin_ecommerce.product.service;

import com.ecommer_admin.admin_ecommerce.common.exception.ConflictException;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.product.dto.CreateProduct;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewProduct;
import com.ecommer_admin.admin_ecommerce.product.dto.ViewProductDto;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductEntity;
import com.ecommer_admin.admin_ecommerce.product.entity.ProductImageEntity;
import com.ecommer_admin.admin_ecommerce.product.repository.ProductImageRepository;
import com.ecommer_admin.admin_ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;

    public ViewProduct addProduct(CreateProduct createProduct) {
        if(productRepository.existsBySku(createProduct.getSku())) {
            throw new ConflictException("Product with this SKU already present.");
        }
        ProductEntity product = modelMapper.map(createProduct , ProductEntity.class);
        productRepository.save(product);
        return modelMapper.map(product , ViewProduct.class);

    }

    public ViewProduct getProductById (Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new
                ResourceNotFoundException("Product with this id not found !"));

        return modelMapper.map(product , ViewProduct.class);

    }

    public ViewProduct updateProduct(CreateProduct createProduct, Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new
                ResourceNotFoundException("Product with this id not found !"));

        if(!createProduct.getSku().equals(product.getSku()) && productRepository.existsBySku(createProduct.getSku())) {
            throw new ConflictException("SKU already present , try different SKU");
        }

        product.setPrice(createProduct.getPrice());
        product.setSku(createProduct.getSku());
        product.setCostPrice(createProduct.getCostPrice());
        product.setStatus(createProduct.getStatus());

        ProductEntity savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct , ViewProduct.class);

    }

    public List<ViewProduct> getAllProducts () {
//        List<ProductEntity> products = productRepository.findAll();
        List<ProductEntity> products = productRepository.getAllProducts();
        return products.stream().map(res -> modelMapper.map(res , ViewProduct.class)).toList();
    }

    public ViewProduct deleteProduct (Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new
                ResourceNotFoundException("Product with this id not found !"));

        productRepository.delete(product);
        return modelMapper.map(product , ViewProduct.class);
    }

    public ViewProduct deleteProductImage (Long productId , Long productImageId) {
//        delete product image from parent , for this we have to add orphanRemoval = true
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new
                ResourceNotFoundException("Product with this id not found !"));

        ProductImageEntity productImage = productImageRepository.findById(productImageId).orElseThrow(() ->
            new ResourceNotFoundException("Image not found")
        );

//        without orphanRemoval = true , the image will be disassociated with this product but will be present in ProductImage table
        product.getProductImages().remove(productImage);
//        productImage.setProduct(null);
        productRepository.save(product);
//        productImageRepository.save(productImage);

        return modelMapper.map(product , ViewProduct.class);
    }

    public List<ViewProductDto> getAllProducts1() {
        Pageable pageable = PageRequest.of(0 , 10);
        Page<Long> page = productRepository.findProductIds(pageable);
//        System.out.println(page.getContent() + " pages ashish");
        List<ProductEntity> products = productRepository.findAllByIdWithImages(page.getContent());
        return products.stream().map(res -> modelMapper.map(res , ViewProductDto.class)).toList();
    }
}
