package com.piseth.example.spring.phone_shop.controller;

import com.piseth.example.spring.phone_shop.dto.BrandDTO;
import com.piseth.example.spring.phone_shop.dto.PageDTO;
import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.mapper.BrandMapper;
import com.piseth.example.spring.phone_shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO) {
//        Brand brand = Mapper.brand(brandDTO);
        Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
        brand = brandService.create(brand);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOneBrand(@PathVariable("id") Integer brandId) {
        Brand brand = brandService.getById(brandId);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer brandId, @RequestBody BrandDTO brandDTO) {
        Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
        Brand updateBrand = brandService.update(brandId, brand);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(updateBrand));
    }

//    @GetMapping()
//    public ResponseEntity<?> getBrands() {
//        List<BrandDTO> list = brandService.getBrands()
//                .stream()
//                // First Choice
////                .map(brand -> BrandMapper.INSTANCE.toBrandDTO(brand))
//                // Second Choice
//                .map(BrandMapper.INSTANCE::toBrandDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(list);
//        //        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
//    }

    @GetMapping
    public ResponseEntity<?> getBrands(@RequestParam Map<String, String> map) {
        Page<Brand> page = brandService.getBrands(map);
        PageDTO pageDTO = new PageDTO(page);
        /*
        List<BrandDTO> list = brandService.getBrands(map)
                .stream()
                // First Choice
//                .map(brand -> BrandMapper.INSTANCE.toBrandDTO(brand))
                // Second Choice
                .map(BrandMapper.INSTANCE::toBrandDTO)
                .collect(Collectors.toList());
        */
        return ResponseEntity.ok(pageDTO);
        //        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
    }
}
