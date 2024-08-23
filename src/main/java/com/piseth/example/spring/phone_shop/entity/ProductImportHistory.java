package com.piseth.example.spring.phone_shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "productImportHistories")
public class ProductImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_id")
    private Long id;
    @Column(name = "date_import")
    private LocalDateTime dateImport;
    @Column(name = "import_unit")
    private Integer importUnit;
    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
