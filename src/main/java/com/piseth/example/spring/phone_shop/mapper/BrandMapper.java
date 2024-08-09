package com.piseth.example.spring.phone_shop.mapper;

import com.piseth.example.spring.phone_shop.dto.BrandDTO;
import com.piseth.example.spring.phone_shop.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {
    //    Mappers.getMapper(BrandMapper.class);
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    Brand toBrand(BrandDTO brandDTO);

    BrandDTO toBrandDTO(Brand brand);
}
