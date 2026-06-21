package com.ecommer_admin.admin_ecommerce.brand.controller;

import com.ecommer_admin.admin_ecommerce.brand.dto.CreateBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.dto.ViewBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import com.ecommer_admin.admin_ecommerce.brand.service.BrandService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/create")
    public ViewBrandDto createBrand (@RequestBody @Valid CreateBrandDto createBrandDto) {
        return brandService.createBrand(createBrandDto);
    }
}
