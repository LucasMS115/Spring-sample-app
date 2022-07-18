package io.github.LucasMS115.spring_sales.rest;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class ApiErrors {
    @Getter
    private final List<String> errors;

    public ApiErrors(String errorMsg){
        this.errors = Collections.singletonList(errorMsg);
    }
}
