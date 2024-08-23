package com.piseth.example.spring.phone_shop.spec;

import com.piseth.example.spring.phone_shop.entity.ProductImportHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ProductImportHistorySpec implements Specification<ProductImportHistory> {
    private ProductImportHistoryFilter productImportHistoryFilter;
    @Override
    public Predicate toPredicate(Root<ProductImportHistory> productImportHistoryRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull((productImportHistoryFilter.getStartDate()))){
            criteriaBuilder.greaterThanOrEqualTo(productImportHistoryRoot.get("dateImport"), productImportHistoryFilter.getStartDate());
        }
        if(Objects.nonNull((productImportHistoryFilter.getEndDate()))){
            criteriaBuilder.lessThanOrEqualTo(productImportHistoryRoot.get("dateImport"), productImportHistoryFilter.getEndDate());
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
