package com.integu.basic.spring.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.integu.basic.spring.models.Bank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.integu.basic.spring.validations.ValidationMessages.FIELD_CANNOT_BE_LEFT_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String name;
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String currency;
    @NotNull(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private long balance;
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String owner;
    private LocalDateTime created;
    private LocalDateTime modified;
    @JsonIgnore
    private Bank bank;
}
