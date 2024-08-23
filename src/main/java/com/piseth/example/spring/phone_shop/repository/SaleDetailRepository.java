package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>, JpaSpecificationExecutor<SaleDetail> {
    List<SaleDetail> findBySaleId(Long saleId);
}
