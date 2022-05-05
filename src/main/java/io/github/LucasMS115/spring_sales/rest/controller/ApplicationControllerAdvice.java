package io.github.LucasMS115.spring_sales.rest.controller;

import io.github.LucasMS115.spring_sales.exception.BusinessRulesException;
import io.github.LucasMS115.spring_sales.exception.OrderNotFoundException;
import io.github.LucasMS115.spring_sales.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRulesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessRulesException(BusinessRulesException e) {
        String errorMsg = e.getMessage();
        return new ApiErrors(errorMsg);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleOrderNotFoundException(OrderNotFoundException e) {
        String errorMsg = e.getMessage();
        return new ApiErrors(errorMsg);
    }

}
