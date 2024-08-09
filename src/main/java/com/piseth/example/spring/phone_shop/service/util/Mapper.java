package com.piseth.example.spring.phone_shop.service.util;

import com.piseth.example.spring.phone_shop.dto.BrandDTO;
import com.piseth.example.spring.phone_shop.entity.Brand;

public class Mapper {
    public static Brand brand(BrandDTO brandDTO) {
        Brand brand = new Brand();
//        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        return brand;
    }

    public static BrandDTO brandDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName(brand.getName());
        return brandDTO;
    }
}
