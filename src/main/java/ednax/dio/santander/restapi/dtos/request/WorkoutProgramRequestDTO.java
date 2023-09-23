package ednax.dio.santander.restapi.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record WorkoutProgramRequestDTO(
    String name,
    @NotBlank String objective
) {}
