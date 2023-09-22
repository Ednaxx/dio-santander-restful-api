package ednax.dio.santander.restapi.dtos.response;

import java.math.BigDecimal;

public record UserResponseDTO (
    String firstName, String surname, String sex, Integer age, BigDecimal weight, BigDecimal height
    ) {}
