package com.openconsigplatform.credit.interfaces;

import com.openconsigplatform.credit.domain.InvalidCreditSimulationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CreditExceptionHandler {

    @ExceptionHandler(InvalidCreditSimulationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleInvalidSimulation(InvalidCreditSimulationException exception) {
        return new ErrorResponse("INVALID_REQUEST", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleValidation(MethodArgumentNotValidException exception) {
        FieldError error = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .orElse(null);
        String message = error == null ? "Request contains invalid data" : error.getDefaultMessage();
        return new ErrorResponse("INVALID_REQUEST", message);
    }

    record ErrorResponse(String code, String message) {
    }
}
