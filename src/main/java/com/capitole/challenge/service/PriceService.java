package com.capitole.challenge.service;

import com.capitole.challenge.converter.PriceToPriceDTOConverter;
import com.capitole.challenge.exception.PriceAvailabilityException;
import com.capitole.challenge.model.domain.Price;
import com.capitole.challenge.model.dto.PriceDetailRequest;
import com.capitole.challenge.model.dto.PriceDetailResponse;
import com.capitole.challenge.repository.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class PriceService {

    private final PriceToPriceDTOConverter priceToPriceDTOConverter;
    private final PriceRepository priceRepository;

    public PriceDetailResponse obtainPriceDetailByRequest(final PriceDetailRequest priceDetailRequest)
            throws PriceAvailabilityException {

        log.info(String.format("Find availability in database for product %s, date %s and brand %s",
                priceDetailRequest.getProductId(), priceDetailRequest.getApplicationDate(), priceDetailRequest.getBrand()));

        final Optional<Price> priceDetailOpt = this.priceRepository.findProductAvailability(
                priceDetailRequest.getApplicationDate(), priceDetailRequest.getProductId(),
                priceDetailRequest.getBrand()).stream().max(Comparator.comparing(Price::getPriority));

        return priceDetailOpt
                .map(this.priceToPriceDTOConverter::convert)
                .orElseThrow(() -> new PriceAvailabilityException("Price not available!"));

    }
}
