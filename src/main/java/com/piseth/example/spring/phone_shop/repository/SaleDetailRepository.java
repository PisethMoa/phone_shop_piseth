package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}
