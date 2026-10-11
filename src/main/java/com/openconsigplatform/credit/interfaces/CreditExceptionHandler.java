package com.openconsigplatform.credit.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CreditExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleInvalidRequest(IllegalArgumentException exception) {
        return new ErrorResponse("INVALID_REQUEST", exception.getMessage());
    }

    record ErrorResponse(String code, String message) {
    }
}
