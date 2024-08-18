package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    List<Model> findByBrandId(Integer brandId);
}
