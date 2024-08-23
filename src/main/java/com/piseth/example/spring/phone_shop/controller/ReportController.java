package com.piseth.example.spring.phone_shop.controller;

import com.piseth.example.spring.phone_shop.dto.ProductReportDTO;
import com.piseth.example.spring.phone_shop.dto.report.ExpenseReportDTO;
import com.piseth.example.spring.phone_shop.projection.ProductSold;
import com.piseth.example.spring.phone_shop.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("{startDate}/{endDate}")
    public ResponseEntity<?> productSold(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate) {
        List<ProductSold> productSoldList = reportService.getProductSold(startDate, endDate);
        return ResponseEntity.ok(productSoldList);
    }

    @GetMapping("v2/{startDate}/{endDate}")
    public ResponseEntity<?> productSoldV2(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate) {
        List<ProductReportDTO> productSoldList = reportService.getProductReport(startDate, endDate);
        return ResponseEntity.ok(productSoldList);
    }
    @GetMapping("expense/{startDate}/{endDate}")
    public ResponseEntity<?> expenseReport(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate) {
        List<ExpenseReportDTO> list = reportService.getExpenseReport(startDate, endDate);
        return ResponseEntity.ok(list);
    }
}
