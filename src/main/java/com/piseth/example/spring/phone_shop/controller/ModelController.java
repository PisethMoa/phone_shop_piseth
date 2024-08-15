package com.piseth.example.spring.phone_shop.controller;

import com.piseth.example.spring.phone_shop.dto.ModelDTO;
import com.piseth.example.spring.phone_shop.entity.Model;
import com.piseth.example.spring.phone_shop.mapper.ModelMapper;
import com.piseth.example.spring.phone_shop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {
    //    @Autowired
    private final ModelService modelService;
    private final ModelMapper modelMapper;

    //    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ModelDTO modelDTO) {
        Model model = modelMapper.model(modelDTO);
        model = modelService.save(model);
        return ResponseEntity.ok(modelMapper.modelDTO(model));
    }
}