package com.capitole.challenge.service;

import com.capitole.challenge.converter.PriceToPriceDTOConverter;
import com.capitole.challenge.exception.PriceAvailabilityException;
import com.capitole.challenge.model.domain.Price;
import com.capitole.challenge.model.dto.PriceDetailRequest;
import com.capitole.challenge.model.dto.PriceDetailResponse;
import com.capitole.challenge.repository.PriceRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    private static final PriceDetailRequest PRICE_DETAIL_REQUEST = PriceDetailRequest.builder()
            .applicationDate(LocalDateTime.of(2020, Month.JUNE, 15, 0, 0))
            .productId(NumberUtils.LONG_ONE)
            .brand(NumberUtils.LONG_ONE)
            .build();

    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceToPriceDTOConverter priceToPriceDTOConverter;

    @Mock
    private PriceRepository priceRepository;

    @Captor
    private ArgumentCaptor<Price> priceArgumentCaptor;


    @Test
    void should_return_price_availability_for_request() throws PriceAvailabilityException {

        final PriceDetailResponse priceDetailResponseExpected = PriceDetailResponse.builder()
                .price(new BigDecimal("100"))
                .build();

        final Price priceMajorPriority = Price.builder()
                .id(1)
                .priority(NumberUtils.LONG_ONE)
                .build();

        final Price priceMinorPriority = Price.builder()
                .id(2)
                .priority(NumberUtils.LONG_ZERO)
                .build();

        final List<Price> pricesFromDatabase = List.of(priceMajorPriority,
                priceMinorPriority);

        given(this.priceRepository.findProductAvailability(PRICE_DETAIL_REQUEST.getApplicationDate(),
                PRICE_DETAIL_REQUEST.getProductId(), PRICE_DETAIL_REQUEST.getBrand()))
                .willReturn(pricesFromDatabase);


        given(this.priceToPriceDTOConverter.convert(priceArgumentCaptor.capture()))
                .willReturn(priceDetailResponseExpected);

        final PriceDetailResponse priceDetailResponse = this.priceService.obtainPriceDetailByRequest(PRICE_DETAIL_REQUEST);

        assertThat(priceArgumentCaptor.getValue().getPriority(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse, is(priceDetailResponseExpected));

    }

    @Test
    void should_throw_availability_exception_because_price_is_not_found() {

        assertThrows(PriceAvailabilityException.class, () -> this.priceService.obtainPriceDetailByRequest(PRICE_DETAIL_REQUEST));

    }
}