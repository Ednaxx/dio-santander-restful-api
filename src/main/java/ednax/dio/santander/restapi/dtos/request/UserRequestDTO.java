package ednax.dio.santander.restapi.dtos.request;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
    @NotBlank String firstName,
    @NotBlank String surname,
    @NotBlank String sex,
    @NotNull @DateTimeFormat(pattern = "dd/MM/yyyy") Date birthday,
    @NotNull @Min(0) @Max(999) @Digits(integer = 3, fraction = 2) BigDecimal weight,
    @NotNull @Min(0) @Max(999) @Digits(integer = 3, fraction = 2) BigDecimal height
    ) {}
