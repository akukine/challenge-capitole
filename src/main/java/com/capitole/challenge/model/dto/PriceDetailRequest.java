package com.capitole.challenge.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class PriceDetailRequest {

    @NotNull(message = "applicationDate shouldn't null or empty")
    LocalDateTime applicationDate;
    @NotNull(message = "productId shouldn't null or empty")
    Long productId;
    @NotNull(message = "brand shouldn't null or empty")
    Long brand;

}
