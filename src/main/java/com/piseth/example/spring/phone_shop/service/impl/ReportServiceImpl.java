package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.projection.ProductSold;
import com.piseth.example.spring.phone_shop.repository.SaleRepository;
import com.piseth.example.spring.phone_shop.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final SaleRepository saleRepository;

    @Override
    public List<ProductSold> getProductSold(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findProductSold(startDate, endDate);
    }
}
