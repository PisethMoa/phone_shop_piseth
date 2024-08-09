package com.piseth.example.spring.phone_shop.controller;

import com.piseth.example.spring.phone_shop.dto.BrandDTO;
import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.service.BrandService;
import com.piseth.example.spring.phone_shop.service.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO) {
        Brand brand = Mapper.brand(brandDTO);
        brand = brandService.create(brand);
        return ResponseEntity.ok(Mapper.brandDTO(brand));
    }
}
