package com.integu.basic.spring.validations;

import lombok.Getter;

@Getter
public class Validation {
    private final String value;
    private final String message;

    public Validation(String value, String message) {
        this.value = value;
        this.message = message;
    }

}
