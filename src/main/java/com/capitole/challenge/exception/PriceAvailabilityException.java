package com.capitole.challenge.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PriceAvailabilityException extends Exception {

    public PriceAvailabilityException(final String message) {
        super(message);
    }
}
