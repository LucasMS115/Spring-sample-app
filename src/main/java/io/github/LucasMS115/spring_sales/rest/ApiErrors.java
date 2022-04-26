package io.github.LucasMS115.spring_sales.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String errorMsg){
        this.errors = Arrays.asList(errorMsg);
    }
}
