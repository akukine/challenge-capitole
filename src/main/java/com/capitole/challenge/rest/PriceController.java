package com.capitole.challenge.rest;

import com.capitole.challenge.exception.PriceAvailabilityException;
import com.capitole.challenge.model.dto.PriceDetailRequest;
import com.capitole.challenge.model.dto.PriceDetailResponse;
import com.capitole.challenge.service.PriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
@AllArgsConstructor
@Slf4j
public class PriceController {

    private final PriceService priceService;

    @PostMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDetailResponse> obtainPriceByRequest(@Validated @RequestBody final PriceDetailRequest priceDetailRequest)
            throws PriceAvailabilityException {

        log.info(String.format("Find price availability for product %s, date %s and brand %s",
                priceDetailRequest.getProductId(), priceDetailRequest.getApplicationDate(), priceDetailRequest.getBrand()));

        return ResponseEntity.ok(this.priceService.obtainPriceDetailByRequest(priceDetailRequest));
    }
}
