package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    List<Brand> findByNameLike(String name);

    // Homework for do junit test with h2 database
    List<Brand> findByNameContaining(String name);
}
