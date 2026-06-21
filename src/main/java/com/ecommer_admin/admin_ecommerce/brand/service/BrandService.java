package com.ecommer_admin.admin_ecommerce.brand.service;

import com.ecommer_admin.admin_ecommerce.brand.dto.CreateBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.dto.ViewBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import com.ecommer_admin.admin_ecommerce.brand.repository.BrandRepository;
import com.ecommer_admin.admin_ecommerce.common.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public ViewBrandDto createBrand(CreateBrandDto createBrandDto) {

        if(brandRepository.existsByName(createBrandDto.getName())) {
           throw new ConflictException("Brand Name already exists");
        }

        BrandEntity brand = modelMapper.map(createBrandDto , BrandEntity.class);
        BrandEntity savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand , ViewBrandDto.class);
    }
}
