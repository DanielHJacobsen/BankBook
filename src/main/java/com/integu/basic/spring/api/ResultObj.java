package com.integu.basic.spring.api;

import com.integu.basic.spring.validations.Validation;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultObj<T> {
    private final Result result;

    @Nullable
    private T object;

    @Nullable
    private Validation validation;

    public enum Result {
        SUCCESS,
        VALIDATION,
        ERROR
    }

}


