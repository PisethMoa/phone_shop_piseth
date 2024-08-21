package com.piseth.example.spring.phone_shop.mapper;

import com.piseth.example.spring.phone_shop.dto.ProductDTO;
import com.piseth.example.spring.phone_shop.dto.ProductImportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;
import com.piseth.example.spring.phone_shop.service.ColorService;
import com.piseth.example.spring.phone_shop.service.ModelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {ModelService.class, ColorService.class})
public interface ProductMapper {
    @Mapping(target = "model", source = "modelId")
    @Mapping(target = "color", source = "colorId")
    Product product(ProductDTO productDTO);

    @Mapping(target = "dateImport", source = "productImportDTO.importDate")
    @Mapping(target = "pricePerUnit", source = "productImportDTO.importPrice")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    ProductImportHistory productImportHistory(ProductImportDTO productImportDTO, Product product);
}
