package com.capitole.challenge.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class PriceDetailResponse {

    Long productId;
    Long brandId;
    Long priceList;
    LocalDateTime startPriceDate;
    LocalDateTime endPriceDate;
    BigDecimal price;

}
