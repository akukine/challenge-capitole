package com.capitole.challenge.repository;

import com.capitole.challenge.model.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT price " +
            "FROM Price price " +
            "INNER JOIN price.brand brand " +
            "INNER JOIN price.product product " +
            "where :startDate between price.startDate and price.endDate " +
            "and product.id = :productId and brand.id = :brandId")
    List<Price> findProductAvailability(@Param("startDate") LocalDateTime startDate,
                                        @Param("productId") Long productId,
                                        @Param("brandId") Long brandId);


}
