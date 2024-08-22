package com.piseth.example.spring.phone_shop.projection;

import java.math.BigDecimal;

public interface ProductSold {
    Long getProductId();

    String getProductName();

    Integer getUnit();

    BigDecimal getTotalAmount();
}
