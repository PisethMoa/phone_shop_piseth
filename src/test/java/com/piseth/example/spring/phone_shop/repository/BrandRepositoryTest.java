package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@DataJpaTest
public class BrandRepositoryTest {
    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testFindByNameLike() {
        // given
        Brand brand = new Brand();
        brand.setName("Apple");
        Brand brand1 = new Brand();
        brand1.setName("Samsung");
        brandRepository.save(brand);
        brandRepository.save(brand1);
        // when
        List<Brand> list = brandRepository.findByNameLike("%A%");
        // then
        assertEquals(1, list.size());
        assertEquals("Apple", list.getFirst().getName());
//        assertEquals(1, list.getFirst().getId());
    }

    // Homework use junit test with h2 database
    @Test
    public void testFindByNameContaining() {
        // given
        Brand brand = new Brand();
        brand.setName("Honor");
        brandRepository.save(brand);
        // when
        List<Brand> list = brandRepository.findByNameContaining("H");
        // then
        assertEquals(1, list.size());
        assertEquals("Honor", list.getFirst().getName());
//        assertEquals(1, list.getFirst().getId());
    }
}
