package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.dto.ProductImportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;

import java.math.BigDecimal;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    void importProduct(ProductImportDTO importDTO);

    void setSalePrice(Long productId, BigDecimal price);

    void validateStock(Long productId, Integer numberOfUnit);
}
