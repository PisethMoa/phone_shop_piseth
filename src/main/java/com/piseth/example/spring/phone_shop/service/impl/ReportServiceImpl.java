package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.dto.ProductReportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.SaleDetail;
import com.piseth.example.spring.phone_shop.projection.ProductSold;
import com.piseth.example.spring.phone_shop.repository.ProductRepository;
import com.piseth.example.spring.phone_shop.repository.SaleDetailRepository;
import com.piseth.example.spring.phone_shop.repository.SaleRepository;
import com.piseth.example.spring.phone_shop.service.ReportService;
import com.piseth.example.spring.phone_shop.spec.SaleDetailFilter;
import com.piseth.example.spring.phone_shop.spec.SaleDetailSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductSold> getProductSold(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findProductSold(startDate, endDate);
    }

    @Override
    public List<ProductReportDTO> getProductReport(LocalDate startDate, LocalDate endDate) {
        List<ProductReportDTO> list = new ArrayList<>();
        SaleDetailFilter saleDetailFilter = new SaleDetailFilter();
        saleDetailFilter.setStartDate(startDate);
        saleDetailFilter.setEndDate(endDate);
        Specification<SaleDetail> specification = new SaleDetailSpec(saleDetailFilter);
        List<SaleDetail> saleDetails = saleDetailRepository.findAll(specification);
        List<Long> longs = saleDetails.stream()
                .map(saleDetail -> saleDetail.getProduct().getId())
                .toList();
        Map<Long, Product> map = productRepository.findAllById(longs).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        Map<Product, List<SaleDetail>> map1 = saleDetails.stream()
                .collect(Collectors.groupingBy(SaleDetail::getProduct));
        for (var entry : map1.entrySet()) {
            Product product = entry.getKey();
            List<SaleDetail> saleDetails1 = entry.getValue();
            // total unit
            Integer unit = saleDetails1.stream().map(SaleDetail::getUnit)
                    .reduce(0, Integer::sum);
            /*
            Integer integer = saleDetails1.stream().map(SaleDetail::getUnit)
                    .reduce(Integer::sum)
                    .get();
            */
            /*
            Double totalAmount = saleDetails1.stream()
                    .map(saleDetail -> saleDetail.getUnit() * saleDetail.getAmount().doubleValue())
                    .reduce(0d, Double::sum);
            */
            double totalAmount = saleDetails1.stream()
                    .mapToDouble(sd -> sd.getUnit() * sd.getAmount().doubleValue())
                    .sum();
            ProductReportDTO productReportDTO = new ProductReportDTO();
            productReportDTO.setProductId(product.getId());
            productReportDTO.setProductName(product.getName());
            productReportDTO.setUnit(unit);
            productReportDTO.setTotalAmount(BigDecimal.valueOf(totalAmount));
            list.add(productReportDTO);
        }
        return list;
    }
}
