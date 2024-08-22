package com.piseth.example.spring.phone_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;
    @Column(name = "sold_date")
    private LocalDateTime soldDate;
    private Boolean active;
}
