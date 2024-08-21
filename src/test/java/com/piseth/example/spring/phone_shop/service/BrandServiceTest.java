package com.piseth.example.spring.phone_shop.service;

import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.repository.BrandRepository;
import com.piseth.example.spring.phone_shop.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;
    private BrandService brandService;

    @BeforeEach
    public void setUp() {
        brandService = new BrandServiceImpl(brandRepository);
    }

    /*
    @Test
    public void testCreate() {
        // given
        Brand brand = new Brand();
        brand.setName("Apple");
        brand.setId(1);
        Brand brand2 = new Brand();
        brand2.setName("Apple");
        // when
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
//        when(brandRepository.save(brand2)).thenReturn(brand);
        Brand brand1 = brandService.create(new Brand());
        // then
        assertEquals(1, brand1.getId());
        assertEquals("Apple", brand1.getName());
    }
    */
    @Test
    public void testCreate() {
        // given
        Brand brand = new Brand();
        brand.setName("Apple");
        // when
        brandService.create(brand);
        // then
        verify(brandRepository, times(1)).save(brand);
//        verify(brandRepository, times(1)).delete(brand);
    }

    @Test
    public void testGetByIdSuccess() {
        // given
        Brand brand = new Brand();
        brand.setName("Apple");
        brand.setId(1L);
        // when
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        Brand brand1 = brandService.getById(1L);
        // then
        assertEquals(1, brand1.getId());
        assertEquals("Apple", brand1.getName());
    }

    @Test
    public void testGetByIdThrow() {
        // given
        // when
        when(brandRepository.findById(2L)).thenReturn(Optional.empty());
//        brandService.getById(2);
        assertThatThrownBy(() -> brandService.getById(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Brand with id = 2 not found.");
//                .hasMessage(String.format("%s with id = %d not found.", "Brand", 2));
//                .hasMessageEndingWith("not found");
        // then
    }
}
