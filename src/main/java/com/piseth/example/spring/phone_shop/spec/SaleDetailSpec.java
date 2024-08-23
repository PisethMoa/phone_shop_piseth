package com.piseth.example.spring.phone_shop.spec;

import com.piseth.example.spring.phone_shop.entity.Sale;
import com.piseth.example.spring.phone_shop.entity.SaleDetail;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SaleDetailSpec implements Specification<SaleDetail> {
    private SaleDetailFilter saleDetailFilter;

    @Override
    public Predicate toPredicate(Root<SaleDetail> saleDetailRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<SaleDetail, Sale> sale = saleDetailRoot.join("sale");
        if (Objects.nonNull(saleDetailFilter.getStartDate())) { // saleDetailFilter.getStartDate() != null
            criteriaBuilder.greaterThanOrEqualTo(sale.get("soldDate"), saleDetailFilter.getStartDate());
        }
        if (Objects.nonNull(saleDetailFilter.getEndDate())) { // saleDetailFilter.getStartDate() != null
            criteriaBuilder.lessThanOrEqualTo(sale.get("soldDate"), saleDetailFilter.getEndDate());
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
