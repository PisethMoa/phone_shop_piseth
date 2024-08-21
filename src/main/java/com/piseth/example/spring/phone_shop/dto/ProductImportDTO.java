package com.piseth.example.spring.phone_shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductImportDTO {
    @NotNull(message = "Product id can't be null.")
    private Long productId;
    @Min(value = 1, message = "Import unit must be greater than 0.")
    private Integer importUnit;
    @DecimalMin(value = "0.000001", message = "Price must be greater than 0.")
    private BigDecimal importPrice;
    @NotNull(message = "Import date can't be null.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime importDate;
}