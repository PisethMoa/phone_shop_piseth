package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImportHistoryRepository extends JpaRepository<ProductImportHistory, Long> {

}
