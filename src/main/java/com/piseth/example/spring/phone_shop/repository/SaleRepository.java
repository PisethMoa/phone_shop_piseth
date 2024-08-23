package com.piseth.example.spring.phone_shop.repository;

import com.piseth.example.spring.phone_shop.entity.Sale;
import com.piseth.example.spring.phone_shop.projection.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = "select p.id as productId, p.name as productName, sum(sd.unit) as unit, sum(sd.unit * sd.sold_amount) as totalAmount " +
            "from sale_details sd " +
            "inner join sales s on sd.sale_id = s.sale_id " +
            "inner join products p on p.id = sd.product_id " +
            "where s.sold_date between :startDate and :endDate " +
            "group by p.id, p.name", nativeQuery = true)
    List<ProductSold> findProductSold(LocalDate startDate, LocalDate endDate);
}
