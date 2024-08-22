package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.dto.SaleDTO;
import com.piseth.example.spring.phone_shop.entity.Sale;

public interface SaleService {
    void sell(SaleDTO saleDTO);

    Sale getById(Long saleId);

    void cancelSale(Long saleId);
}
