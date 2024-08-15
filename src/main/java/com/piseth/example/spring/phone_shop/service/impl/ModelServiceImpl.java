package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.entity.Model;
import com.piseth.example.spring.phone_shop.repository.ModelRepository;
import com.piseth.example.spring.phone_shop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ModelServiceImpl implements ModelService {
    //    @Autowired
    private final ModelRepository modelRepository;
//    @Autowired
//    private BrandService brandService;

    @Override
    public Model save(Model model) {
//        Integer brandId = model.getBrand().getId();
//        brandService.getById(brandId);
//        Model model = modelMapper.model(modelDTO);
        return modelRepository.save(model);
    }
}
