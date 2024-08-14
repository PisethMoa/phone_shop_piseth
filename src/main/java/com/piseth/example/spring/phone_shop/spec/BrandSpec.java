package com.piseth.example.spring.phone_shop.spec;

import com.piseth.example.spring.phone_shop.entity.Brand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class BrandSpec implements Specification<Brand> {
    private final BrandFilter brandFilter;
    List<Predicate> list = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<Brand> brand, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (brandFilter.getName() != null) {
            // First Choice
//            Predicate name = brand.get("name").in(brandFilter.getName());
            // Second Choice
            Predicate name = criteriaBuilder.like(criteriaBuilder.upper(brand.get("name")), "%" + brandFilter.getName().toUpperCase() + "%");
            list.add(name);
        }
        if (brandFilter.getId() != null) {
            Predicate id = brand.get("id").in(brandFilter.getId());
            list.add(id);
        }
        // First Choice
//        return criteriaBuilder.and(list.toArray(new Predicate[0]));
        // Second Choice (Constructor Reference)
        return criteriaBuilder.and(list.toArray(Predicate[]::new));
    }
}
