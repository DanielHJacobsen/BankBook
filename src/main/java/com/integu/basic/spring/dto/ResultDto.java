package com.integu.basic.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class ResultDto {
    @NotEmpty
    private String message;
}
