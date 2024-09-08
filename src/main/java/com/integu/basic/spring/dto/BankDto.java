package com.integu.basic.spring.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;

import static com.integu.basic.spring.validations.ValidationMessages.*;

@Data
@Builder
public class BankDto {
    private Long id;
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String name;
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String address;
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String phone;
    @URL(message = "Must be a valid URL.")
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String imageUrl;
    @Email(message = "Must be a valid email format")
    @NotEmpty(message = FIELD_CANNOT_BE_LEFT_EMPTY)
    private String email;
    @Min(value = 1, message = RATING_BETWEEN_1_3)
    @Max(value = 3, message = RATING_BETWEEN_1_3)
    private Integer rating;
    private List<AccountDto> accounts;
    private LocalDateTime created;
    private LocalDateTime modified;
}
