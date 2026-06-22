package com.ecommer_admin.admin_ecommerce.brand.service;

import com.ecommer_admin.admin_ecommerce.brand.dto.CreateBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.dto.ViewBrandDto;
import com.ecommer_admin.admin_ecommerce.brand.entity.BrandEntity;
import com.ecommer_admin.admin_ecommerce.brand.repository.BrandRepository;
import com.ecommer_admin.admin_ecommerce.common.exception.ConflictException;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;
    private final int PAGE_SIZE = 5;

    public ViewBrandDto createBrand(CreateBrandDto createBrandDto) {

        if(brandRepository.existsByName(createBrandDto.getName())) {
           throw new ConflictException("Brand Name already exists");
        }

        BrandEntity brand = modelMapper.map(createBrandDto , BrandEntity.class);
        BrandEntity savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand , ViewBrandDto.class);
    }

    public ViewBrandDto updateBrand(@Valid CreateBrandDto createBrandDto, Long brandId) {

        BrandEntity brand = brandRepository.findById(brandId).orElseThrow(() ->
                        new ResourceNotFoundException("Brand with given id not found !")
                );

       if(!createBrandDto.getName().equals(brand.getName()) && brandRepository.existsByName(createBrandDto.getName())) {
           throw new ConflictException("Brand name already exists");
       }

       brand.setName(createBrandDto.getName());
       brand.setDescription(createBrandDto.getDescription());
       brand.setLogoUrl(createBrandDto.getLogoUrl());
       brand.setStatus(createBrandDto.getStatus());

       return modelMapper.map(brandRepository.save(brand) , ViewBrandDto.class);

    }

    public List<ViewBrandDto> getAllBrands(String name, String description , int pageNumber , String sortOrder) {



        Sort sort = Sort.by(
                "ASC".equalsIgnoreCase(sortOrder)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                "name" , "description"
        );
        Pageable pageable = PageRequest.of(pageNumber - 1 , PAGE_SIZE , sort);

//        PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));
        List<BrandEntity> brands = brandRepository.findAllByNameAndDesc(name , description , pageable);

//        if(name == null || name.isBlank()) {
//            brands = brandRepository.findAll();
//        } else {
//
//            brands = brandRepository.findAllByName(name);  // handle this on query level
//        }
        return brands.stream().map(res -> modelMapper.map(res , ViewBrandDto.class)).toList();
    }


    public ViewBrandDto getBrandById(Long brandId) {
        BrandEntity brand = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Brand with given id not found !"));
        return modelMapper.map(brand , ViewBrandDto.class);
    }
}
