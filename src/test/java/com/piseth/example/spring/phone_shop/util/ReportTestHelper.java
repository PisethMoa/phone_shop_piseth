package com.piseth.example.spring.phone_shop.util;

import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReportTestHelper {
    private static Product product = Product.builder()
            .id(1L)
            .name("iPhone silver")
            .build();
    private static Product product1 = Product.builder()
            .id(2L)
            .name("iPhone black")
            .build();
    private static Product product2 = Product.builder()
            .id(3L)
            .name("Galaxy Tab 3 Plus 10.1 P8220")
            .build();

    public static List<Product> getProducts() {
        return List.of(product, product1);
    }

    public static List<ProductImportHistory> getProductImportHistories() {
        ProductImportHistory productImportHistory = ProductImportHistory.builder()
                .product(product)
                .importUnit(10)
                .pricePerUnit(BigDecimal.valueOf(599))
                .dateImport(LocalDateTime.of(2023, 8, 23, 8, 50))
                .build();
        ProductImportHistory productImportHistory1 = ProductImportHistory.builder()
                .product(product1)
                .importUnit(15)
                .pricePerUnit(BigDecimal.valueOf(699))
                .dateImport(LocalDateTime.of(2023, 8, 24, 8, 52))
                .build();
        ProductImportHistory productImportHistory2 = ProductImportHistory.builder()
                .product(product)
                .importUnit(5)
                .pricePerUnit(BigDecimal.valueOf(699))
                .dateImport(LocalDateTime.of(2023, 8, 25, 8, 52))
                .build();
        return List.of(productImportHistory, productImportHistory1, productImportHistory2);
    }
}
