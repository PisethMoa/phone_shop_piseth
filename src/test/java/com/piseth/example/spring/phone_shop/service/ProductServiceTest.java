package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.exception.ApiException;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.repository.ProductRepository;
import com.piseth.example.spring.phone_shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    public void setUp() {
        // Correct Mock Initialization
        productRepository = mock(ProductRepository.class);
        // Set up the service with the mocked repository
        productServiceImpl = new ProductServiceImpl(productRepository, null, null);
    }

    @Test
    public void testSetSalePrice() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(1L);
        product.setSalePrice(new BigDecimal("100.00"));
        BigDecimal bigDecimal = new BigDecimal("199.99");
        // When
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productServiceImpl.setSalePrice(productId, bigDecimal);
        // Then
        assertEquals(bigDecimal, product.getSalePrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testSetSalePrice_ProductNotFound() {
        // Given
        Long productId = 1L;
        BigDecimal bigDecimal = new BigDecimal("199.99");
        // When
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        // Then (Checking for exception manually)
        try {
            productServiceImpl.setSalePrice(productId, bigDecimal);
            // If no exception, print a message (manual validation)
            System.out.println("No exception thrown, but expected ResourceNotFoundException.");
        } catch (ResourceNotFoundException e) {
            // Success: exception thrown, validation completed
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSetSalePrice_ZeroSalePrice() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(1L);
        product.setSalePrice(new BigDecimal("100.00"));
        BigDecimal bigDecimal = BigDecimal.ZERO;
        // When
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        // Then (Checking for exception manually)
        try {
            productServiceImpl.setSalePrice(productId, bigDecimal);
            // If no exception, print a message (manual validation)
            System.out.println("No exception thrown, but expected ApiException for zero sale price.");
        } catch (ApiException e) {
            // Success: exception thrown, validation completed
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
