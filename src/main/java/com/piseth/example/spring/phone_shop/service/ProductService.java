package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.dto.ProductImportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public interface ProductService {
    Product create(Product product);

    Product getById(Long id);

    Product getByModelIdAndColorId(Long modelId, Long colorId);

    void importProduct(ProductImportDTO importDTO);

    void setSalePrice(Long productId, BigDecimal price);

    void validateStock(Long productId, Integer numberOfUnit);

    Map<Integer, String> uploadProduct(MultipartFile file) throws IOException;
}
