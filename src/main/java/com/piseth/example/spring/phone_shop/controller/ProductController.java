package com.piseth.example.spring.phone_shop.controller;

import com.piseth.example.spring.phone_shop.dto.PriceDTO;
import com.piseth.example.spring.phone_shop.dto.ProductDTO;
import com.piseth.example.spring.phone_shop.dto.ProductImportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.mapper.ProductMapper;
import com.piseth.example.spring.phone_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.product(productDTO);
        product = productService.create(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("importProduct")
    public ResponseEntity<?> importProduct(@RequestBody @Valid ProductImportDTO importDTO) {
        productService.importProduct(importDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("{productId}/setSalePrice")
    public ResponseEntity<?> setSalePrice(@PathVariable Long productId, @Valid @RequestBody PriceDTO priceDTO) {
        productService.setSalePrice(productId, priceDTO.getPrice());
        return ResponseEntity.ok().build();
    }
}
