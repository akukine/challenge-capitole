package com.capitole.challenge.rest;

import com.capitole.challenge.model.dto.PriceDetailRequest;
import com.capitole.challenge.model.dto.PriceDetailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest
class PriceControllerTest {

    private static final long PRODUCT_ID = 35455L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void should_return_one_price_list() throws Exception {

        final PriceDetailRequest priceDetailRequest = this.createRequest(
                LocalDateTime.of(2020, Month.JUNE, 14, 10, 0));

        final MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/prices/find")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(priceDetailRequest)))
                .andExpect(status().isOk())
                .andReturn();

        final PriceDetailResponse priceDetailResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(),
                PriceDetailResponse.class);

        assertThat(priceDetailResponse.getPriceList(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getBrandId(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getProductId(), is(PRODUCT_ID));
        assertThat(priceDetailResponse.getPrice(), is(new BigDecimal("35.50")));
        assertThat(priceDetailResponse.getStartPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                14, 0, 0, 0)));
        assertThat(priceDetailResponse.getEndPriceDate(), is(LocalDateTime.of(2020, Month.DECEMBER,
                31, 23, 59, 59)));
    }

    @Test
    void should_return_two_price_list() throws Exception {

        final PriceDetailRequest priceDetailRequest = this.createRequest(
                LocalDateTime.of(2020, Month.JUNE, 14, 16, 0));

        final MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/prices/find")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(priceDetailRequest)))
                .andExpect(status().isOk())
                .andReturn();

        final PriceDetailResponse priceDetailResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(),
                PriceDetailResponse.class);

        assertThat(priceDetailResponse.getPriceList(), is(2L));
        assertThat(priceDetailResponse.getBrandId(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getProductId(), is(PRODUCT_ID));
        assertThat(priceDetailResponse.getPrice(), is(new BigDecimal("25.45")));
        assertThat(priceDetailResponse.getStartPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                14, 15, 0)));
        assertThat(priceDetailResponse.getEndPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                14, 18, 30)));
    }

    @Test
    void should_return_one_price_list_because_priority_is_major() throws Exception {
        final PriceDetailRequest priceDetailRequest = this.createRequest(LocalDateTime.of(2020, Month.JUNE,
                14, 21, 0));

        final MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/prices/find")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(priceDetailRequest)))
                .andExpect(status().isOk())
                .andReturn();

        final PriceDetailResponse priceDetailResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(),
                PriceDetailResponse.class);

        assertThat(priceDetailResponse.getPriceList(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getBrandId(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getProductId(), is(PRODUCT_ID));
        assertThat(priceDetailResponse.getPrice(), is(new BigDecimal("35.50")));
        assertThat(priceDetailResponse.getStartPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                14, 0, 0, 0)));
        assertThat(priceDetailResponse.getEndPriceDate(), is(LocalDateTime.of(2020, Month.DECEMBER,
                31, 23, 59, 59)));
    }

    @Test
    void should_return_three_price_list() throws Exception {
        final PriceDetailRequest priceDetailRequest = this.createRequest(LocalDateTime.of(2020, Month.JUNE,
                15, 10, 0));

        final MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/prices/find")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(priceDetailRequest)))
                .andExpect(status().isOk())
                .andReturn();

        final PriceDetailResponse priceDetailResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(),
                PriceDetailResponse.class);

        assertThat(priceDetailResponse.getPriceList(), is(3L));
        assertThat(priceDetailResponse.getBrandId(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getProductId(), is(PRODUCT_ID));
        assertThat(priceDetailResponse.getPrice(), is(new BigDecimal("30.50")));
        assertThat(priceDetailResponse.getStartPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                15, 0, 0)));
        assertThat(priceDetailResponse.getEndPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                15, 11, 0)));
    }

    @Test
    void should_return_four_price_list() throws Exception {
        final PriceDetailRequest priceDetailRequest = this.createRequest(LocalDateTime.of(2020, Month.JUNE,
                16, 21, 0));

        final MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/prices/find")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(priceDetailRequest)))
                .andExpect(status().isOk())
                .andReturn();

        final PriceDetailResponse priceDetailResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(),
                PriceDetailResponse.class);

        assertThat(priceDetailResponse.getPriceList(), is(4L));
        assertThat(priceDetailResponse.getBrandId(), is(NumberUtils.LONG_ONE));
        assertThat(priceDetailResponse.getProductId(), is(PRODUCT_ID));
        assertThat(priceDetailResponse.getPrice(), is(new BigDecimal("38.95")));
        assertThat(priceDetailResponse.getStartPriceDate(), is(LocalDateTime.of(2020, Month.JUNE,
                15, 16, 0, 0)));
        assertThat(priceDetailResponse.getEndPriceDate(), is(LocalDateTime.of(2020, Month.DECEMBER,
                31, 23, 59, 59)));
    }

    @Test
    void should_return_price_not_available() throws Exception {
        final PriceDetailRequest priceDetailRequest = this.createRequest(LocalDateTime.of(2300, Month.JUNE,
                16, 21, 0));

        this.mockMvc.perform(post("/api/v1/prices/find")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(priceDetailRequest)))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    private PriceDetailRequest createRequest(final LocalDateTime date) {
        return PriceDetailRequest.builder()
                .applicationDate(date)
                .productId(PRODUCT_ID)
                .brand(NumberUtils.LONG_ONE)
                .build();
    }
}