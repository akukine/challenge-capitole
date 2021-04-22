package com.capitole.challenge.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class PriceAdvice {

    @ExceptionHandler(value = PriceAvailabilityException.class)
    public ResponseEntity<String> responseClientNotFound(final Throwable exception) {
        log.error("Error find obtain product availability {}", exception.getMessage());
        return new ResponseEntity<>("Product not available!", HttpStatus.NOT_FOUND);
    }
}
