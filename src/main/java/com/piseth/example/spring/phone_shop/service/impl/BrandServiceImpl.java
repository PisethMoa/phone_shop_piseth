package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.exception.ApiException;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.repository.BrandRepository;
import com.piseth.example.spring.phone_shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Integer id) {
//        Optional<Brand> brandOptional = brandRepository.findById(id);
        /*
        if (brandOptional.isPresent()) {
            return brandOptional.get();
        }
        */
//        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Brand with id = " + id + " not found.");
        // Since: version 1.5
//        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Brand with id = %d not found.", id));
        // Since: version 15
//        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Brand with id = %d not found.".formatted(id));
        return brandRepository.findById(id)
//                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"Brand with id = %d not found.".formatted(id)));
//                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Brand wit id = %d not found.".formatted(id)));
                .orElseThrow(() -> new ResourceNotFoundException("Brand", id));
    }

    @Override
    public Brand update(Integer id, Brand brand) {
        Brand brandId = getById(id);
        brandId.setName(brand.getName()); // @TODO improve update
        return brandRepository.save(brandId);
    }
}
