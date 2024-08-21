package com.piseth.example.spring.phone_shop.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleDTO {
    @NotEmpty
    private List<ProductSoleDTO> productSoleDTO;
    private LocalDateTime saleDate;
}
