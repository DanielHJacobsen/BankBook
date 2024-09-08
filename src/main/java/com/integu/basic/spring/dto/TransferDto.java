package com.integu.basic.spring.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.integu.basic.spring.validations.ValidationMessages.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    @NotNull(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private long fromAccountId;
    @NotNull(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private long toAccountId;
    @Min(value = 1, message = ONLY_POSITIVE_NUMBERS)
    private long amount;
}
