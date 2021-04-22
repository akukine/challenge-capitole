package com.capitole.challenge.converter;

import com.capitole.challenge.model.domain.Price;
import com.capitole.challenge.model.dto.PriceDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceToPriceDTOConverter implements Converter<Price, PriceDetailResponse> {

    @Override
    public PriceDetailResponse convert(final Price price) {
        log.info("Convert price to priceResponse by id {}", price.getId());

        return PriceDetailResponse.builder()
                .productId(price.getProduct().getId())
                .brandId(price.getBrand().getId())
                .priceList(price.getPriceList())
                .startPriceDate(price.getStartDate())
                .endPriceDate(price.getEndDate())
                .price(price.getPrice())
                .build();
    }
}
