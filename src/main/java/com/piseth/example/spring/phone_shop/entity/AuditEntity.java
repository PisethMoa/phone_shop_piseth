package com.piseth.example.spring.phone_shop.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AuditEntity {
    @CreatedDate
    private LocalDateTime dateCreate;
    @LastModifiedDate
    private LocalDateTime dateUpdate;
    @CreatedBy
    private String userCreate;
    @LastModifiedBy
    private String userUpdate;
}
