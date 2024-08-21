package com.piseth.example.spring.phone_shop.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class PriceDTO {
    @DecimalMin(value = "0.000001", message = "Price must be greater than 0.")
    private BigDecimal price;
}
