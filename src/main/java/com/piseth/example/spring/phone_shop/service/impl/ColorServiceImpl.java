package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.entity.Color;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.repository.ColorRepository;
import com.piseth.example.spring.phone_shop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public Color create(Color color) {
        return null;
    }

    @Override
    public Color getById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", id));
    }
}
