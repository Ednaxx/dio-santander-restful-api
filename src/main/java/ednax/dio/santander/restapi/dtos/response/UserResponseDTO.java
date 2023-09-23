package ednax.dio.santander.restapi.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record UserResponseDTO (
    UUID id,
    String firstName,
    String surname,
    String sex,
    Integer age,
    BigDecimal weight,
    BigDecimal height
    ) {}
