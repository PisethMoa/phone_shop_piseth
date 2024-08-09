package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.entity.Brand;

public interface BrandService {
    Brand create(Brand brand);

    Brand getById(Integer id);

    Brand update(Integer id, Brand brand);
}
