package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.entity.Color;

public interface ColorService {
    Color create(Color color);

    Color getById(Long id);
}
