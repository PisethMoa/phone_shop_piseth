package com.piseth.example.spring.phone_shop.spec;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleDetailFilter {
    private LocalDate startDate;
    private LocalDate endDate;
}
