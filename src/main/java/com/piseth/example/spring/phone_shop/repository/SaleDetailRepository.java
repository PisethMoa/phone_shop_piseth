package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
    List<SaleDetail> findBySaleId(Long saleId);
}
