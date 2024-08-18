package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.entity.Model;

import java.util.List;

public interface ModelService {
    Model save(Model model);

    List<Model> getByBrandId(Integer brandId);
}
