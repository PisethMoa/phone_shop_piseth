package com.piseth.example.spring.phone_shop.controller;

import com.piseth.example.spring.phone_shop.dto.SaleDTO;
import com.piseth.example.spring.phone_shop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("sales")
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SaleDTO saleDTO) {
        try {
            saleService.sell(saleDTO);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            // Log the exception and return detailed information
            ex.printStackTrace(); // for development, log it in production
            return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("{saleId}/cancel")
    public ResponseEntity<?> cancelSale(@PathVariable Long saleId) {
        saleService.cancelSale(saleId);
        return ResponseEntity.ok().build();
    }
}
