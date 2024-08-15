package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {
    Brand create(Brand brand);

    Brand getById(Integer id);

    Brand update(Integer id, Brand brand);

//    List<Brand> getBrands();

    List<Brand> getBrands(String name);

    //    List<Brand> getBrands(Map<String, String> map);
    Page<Brand> getBrands(Map<String, String> map);
}
