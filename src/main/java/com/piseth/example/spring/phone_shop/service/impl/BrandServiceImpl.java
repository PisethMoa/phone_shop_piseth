package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.entity.Brand;
import com.piseth.example.spring.phone_shop.exception.ResourceNotFoundException;
import com.piseth.example.spring.phone_shop.repository.BrandRepository;
import com.piseth.example.spring.phone_shop.service.BrandService;
import com.piseth.example.spring.phone_shop.service.util.PageUtil;
import com.piseth.example.spring.phone_shop.spec.BrandFilter;
import com.piseth.example.spring.phone_shop.spec.BrandSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    @Autowired
    private final BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Long id) {
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
    public Brand update(Long id, Brand brand) {
        Brand brandId = getById(id);
        brandId.setName(brand.getName() + "U"); // @TODO improve update
        brandId.setName("Piseth Mao");
        return brandRepository.save(brandId);
    }

//    @Override
//    public List<Brand> getBrands() {
//        return brandRepository.findAll();
//    }

    @Override
    public List<Brand> getBrands(String name) {
//        return brandRepository.findByNameLike("%" + name + "%");
        return brandRepository.findByNameContaining(name);
    }
    /*
    @Override
    public List<Brand> getBrands(Map<String, String> map) {
        BrandFilter brandFilter = new BrandFilter();
        if (map.containsKey("name")) {
            String name = map.get("name");
            brandFilter.setName(name);
        }
        if (map.containsKey("id")) {
            String id = map.get("id");
            brandFilter.setId(Integer.parseInt(id));
        }
        return brandRepository.findAll(brandSpec);
    }
    */

    @Override
    public Page<Brand> getBrands(Map<String, String> map) {
        BrandFilter brandFilter = new BrandFilter();
        if (map.containsKey("name")) {
            String name = map.get("name");
            brandFilter.setName(name);
        }
        if (map.containsKey("id")) {
            String id = map.get("id");
            brandFilter.setId(Integer.parseInt(id));
        }
        // @TODO add to a function for pageable
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if (map.containsKey(PageUtil.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(map.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (map.containsKey(PageUtil.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(map.get(PageUtil.PAGE_NUMBER));
        }
        BrandSpec brandSpec = new BrandSpec(brandFilter);
        Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);
//        Pageable
//        Page<Brand> page = brandRepository.findAll(brandSpec, Pageable.ofSize(0));
        // First Choice
//        Page<Brand> page = brandRepository.findAll(brandSpec, pageable);
        // Second Choice
        return brandRepository.findAll(brandSpec, pageable);
    }
}
