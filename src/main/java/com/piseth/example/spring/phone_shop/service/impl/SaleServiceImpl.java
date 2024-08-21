package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.dto.ProductSoleDTO;
import com.piseth.example.spring.phone_shop.dto.SaleDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.Sale;
import com.piseth.example.spring.phone_shop.entity.SaleDetail;
import com.piseth.example.spring.phone_shop.exception.ApiException;
import com.piseth.example.spring.phone_shop.repository.ProductRepository;
import com.piseth.example.spring.phone_shop.repository.SaleDetailRepository;
import com.piseth.example.spring.phone_shop.repository.SaleRepository;
import com.piseth.example.spring.phone_shop.service.ProductService;
import com.piseth.example.spring.phone_shop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Override
    public void sell(SaleDTO saleDTO) {
        // Validate that the saleDTO and productSoleDTO are not null or empty
        if (saleDTO == null || saleDTO.getProductSoleDTO() == null || saleDTO.getProductSoleDTO().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Product details must not be null or empty.");
        }

        // Extract product IDs from the SaleDTO
        List<Long> productIds = saleDTO.getProductSoleDTO().stream()
                .map(ProductSoleDTO::getProductId)
                .toList();

        // Validate product existence
        productIds.forEach(productService::getById);

        // Fetch products by ID
        List<Product> products = productRepository.findAllById(productIds);

        // Ensure that the product list is not null or empty
        if (products.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No products found for the provided IDs.");
        }

        // Convert a list to map for easy access
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Validate stock
        saleDTO.getProductSoleDTO()
                .forEach(productSoleDTO -> {
                    Product product = productMap.get(productSoleDTO.getProductId());
                    if (product == null || product.getAvailableUnit() < productSoleDTO.getNumberOfUnit()) {
                        throw new ApiException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough in stock.".formatted(product != null ? product.getName() : "Unknown"));
                    }
                });

        // Create and save Sale entity
        Sale sale = new Sale();
        sale.setSoldDate(saleDTO.getSaleDate());
        saleRepository.save(sale);

        // Create and save Sale Details
        saleDTO.getProductSoleDTO().forEach(ps -> {
            Product product = productMap.get(ps.getProductId());
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setAmount(product.getSalePrice());
            saleDetail.setProduct(product);
            saleDetail.setSale(sale);
            saleDetail.setUnit(ps.getNumberOfUnit());
            saleDetailRepository.save(saleDetail);
            // Cut Stock
            Integer availableUnit = product.getAvailableUnit() - ps.getNumberOfUnit();
            product.setAvailableUnit(availableUnit);
            productRepository.save(product);
        });
    }

    private void saveSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setSoldDate(saleDTO.getSaleDate());
        saleRepository.save(sale);
        // Sale Detail
        saleDTO.getProductSoleDTO().forEach(productSoleDTO -> {
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setAmount(null);
        });
    }

    private void validate(SaleDTO saleDTO) {
        saleDTO.getProductSoleDTO().forEach(productSoleDTO -> {
            Product product = productService.getById(productSoleDTO.getProductId());
            if (product.getAvailableUnit() < productSoleDTO.getNumberOfUnit()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough in stock.".formatted(product.getName()));
            }
        });
    }
    /*
    private void validate2(SaleDTO saleDTO){
        List<Long> productIds = saleDTO.getProductSoleDTO().stream()
                .map(ProductSoleDTO::getProductId)
                .toList();
        // validate product
        productIds.forEach(productService::getById);
        List<Product> products = productRepository.findAllById(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        // validate stock
        saleDTO.getProductSoleDTO()
                .forEach(productSoleDTO -> {
                    Product product = productMap.get(productSoleDTO.getProductId());
                    if (product.getAvailableUnit() < productSoleDTO.getNumberOfUnit()){
                        throw new ApiException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough in stock.".formatted(product.getName()));
                    }
                });
    }
    */
}
