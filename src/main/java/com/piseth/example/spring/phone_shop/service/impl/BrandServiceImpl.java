package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.repository.BrandRepository;
import com.piseth.example.spring.phone_shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }
}
