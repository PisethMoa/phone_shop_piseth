package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.dto.ProductImportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;
import com.piseth.example.spring.phone_shop.exception.ApiException;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.mapper.ProductMapper;
import com.piseth.example.spring.phone_shop.repository.ProductImportHistoryRepository;
import com.piseth.example.spring.phone_shop.repository.ProductRepository;
import com.piseth.example.spring.phone_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImportHistoryRepository importHistoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Product create(Product product) {
        String name = "%s %s"
                .formatted(product.getModel().getName(), product.getColor().getName());
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    @Override
    public void importProduct(ProductImportDTO importDTO) {
        // update available product unit
        Product product = getById(importDTO.getProductId());
        Integer availableUnit = 0;
        if (product.getAvailableUnit() != null) {
            availableUnit = product.getAvailableUnit();
        }
        product.setAvailableUnit(availableUnit + importDTO.getImportUnit());
        productRepository.save(product);

        // save product import history
        ProductImportHistory importHistory = productMapper.productImportHistory(importDTO, product);
        importHistoryRepository.save(importHistory);
    }

    @Override
    public void setSalePrice(Long productId, BigDecimal price) {
        // Business logic: price cannot be 0
        if (price.compareTo(BigDecimal.ZERO) == 0) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Price cannot be zero. Please set a valid sale price.");
        }

        // Continue with normal processing
        Product product = getById(productId);
        product.setSalePrice(price);
        productRepository.save(product);
    }

    @Override
    public void validateStock(Long productId, Integer numberOfUnit) {

    }

}
