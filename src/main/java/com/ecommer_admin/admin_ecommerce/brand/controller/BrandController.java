package com.ecommer_admin.admin_ecommerce.brand.controller;

import com.ecommer_admin.admin_ecommerce.brand.dto.CreateBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.dto.ViewBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import com.ecommer_admin.admin_ecommerce.brand.service.BrandService;
import com.ecommer_admin.admin_ecommerce.common.advice.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/brands")
@RequiredArgsConstructor
@Validated
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/create")
    public ViewBrandDto createBrand (@RequestBody @Valid CreateBrandDto createBrandDto) {
        return brandService.createBrand(createBrandDto);
    }

    @PutMapping(path = "/update/{brandId}")
    public ViewBrandDto updateBrand (@RequestBody @Valid CreateBrandDto createBrandDto , @PathVariable Long brandId) {
        return brandService.updateBrand(createBrandDto , brandId);
    }

    @GetMapping(path = "/list")
    public List<ViewBrandDto> getAllBrands (@RequestParam(required = false) String name ,
                                            @RequestParam(required = false) String description ,
                                            @RequestParam  @Positive(message = "pageNumber must be > 0") int pageNumber ,
                                            @RequestParam(required = false , defaultValue = "ASC") String sortOrder
                                            ) {
        return brandService.getAllBrands(name , description , pageNumber , sortOrder); //for sortOrder use enum SortOrder
    }

    @GetMapping(path = "/{brandId}")
    public ViewBrandDto getBrandById (@PathVariable Long brandId) {
        return brandService.getBrandById(brandId);
    }
}
