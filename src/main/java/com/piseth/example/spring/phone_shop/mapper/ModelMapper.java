package com.piseth.example.spring.phone_shop.mapper;

import com.piseth.example.spring.phone_shop.dto.ModelDTO;
import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.entity.Model;
import com.piseth.example.spring.phone_shop.service.BrandService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BrandService.class})
public interface ModelMapper {
    // Mappers
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    @Mapping(target = "brand", source = "brandId")
    Model model(ModelDTO modelDTO);

    @Mapping(target = "brandId", source = "brand.id")
    ModelDTO modelDTO(Model model);
    /*
    default Brand brand(Integer brId) {
        Brand brand = new Brand();
        brand.setId(brId);
        return brand;
    }
    */
}
