package com.ecommer_admin.admin_ecommerce.clients.impl;

import com.ecommer_admin.admin_ecommerce.clients.ProductClient;
import com.ecommer_admin.admin_ecommerce.common.advice.ApiResponse;
import com.ecommer_admin.admin_ecommerce.common.configs.RestClientConfig;
import com.ecommer_admin.admin_ecommerce.product_client.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {
    private final RestClient restClient;

    @Override
    public List<ProductDto> getAllProducts() {
        try {
            List<ProductDto> productDtoList =  restClient.get().uri("users").retrieve().body(new ParameterizedTypeReference<>() {
            });

            return productDtoList;

//            return productDtoList; // we can only wrap in ApiResponse only after receiving from the client
        } catch (Exception e) {
            System.out.println("exception while calling the api");
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public ProductDto getProductById(Long id) {
     try {
         ProductDto product =  restClient.get().uri("/users/{id}", id).retrieve().body(new ParameterizedTypeReference<>() {
         });
         System.out.println(product.getId() + " product id");
         return product;
     } catch (Exception e) {
         System.out.println("exception while calling the api");
         System.out.println(e);
         throw new RuntimeException(e);
     }
    }
}
