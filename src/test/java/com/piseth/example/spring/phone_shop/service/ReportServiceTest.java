package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.dto.report.ExpenseReportDTO;
import com.piseth.example.spring.phone_shop.entity.Product;
import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;
import com.piseth.example.spring.phone_shop.repository.ProductImportHistoryRepository;
import com.piseth.example.spring.phone_shop.repository.ProductRepository;
import com.piseth.example.spring.phone_shop.repository.SaleDetailRepository;
import com.piseth.example.spring.phone_shop.repository.SaleRepository;
import com.piseth.example.spring.phone_shop.service.impl.ReportServiceImpl;
import com.piseth.example.spring.phone_shop.spec.ProductImportHistorySpec;
import com.piseth.example.spring.phone_shop.util.ReportTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SaleDetailRepository saleDetailRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductImportHistoryRepository productImportHistoryRepository;
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        reportService = new ReportServiceImpl(saleRepository, saleDetailRepository, productRepository,
                productImportHistoryRepository);
    }

    @Test
    public void testGetExpenseReport() {
        // Given
        List<ProductImportHistory> list = ReportTestHelper.getProductImportHistories();
        List<Product> list1 = ReportTestHelper.getProducts();
        // When
        when(productImportHistoryRepository.findAll(Mockito.any(ProductImportHistorySpec.class)))
                .thenReturn(list);
        when(productRepository.findAllById(anySet())).thenReturn(list1);
        List<ExpenseReportDTO> list2 = reportService.getExpenseReport(LocalDate.now().minusMonths(8), LocalDate.now());
        // Then
        assertEquals(2, list2.size());
        ExpenseReportDTO expenseReportDTO = list2.getFirst();
        assertEquals(1, expenseReportDTO.getProductId());
        assertEquals("iPhone silver", expenseReportDTO.getProductName());
        assertEquals(15, expenseReportDTO.getTotalUnit());
        assertEquals(9485d, expenseReportDTO.getTotalAmount().doubleValue());
    }
}
