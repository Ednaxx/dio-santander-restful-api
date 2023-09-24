package ednax.dio.santander.restapi.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ExerciseRequestDTO(
    @NotBlank String name,
    @NotBlank String duration,
    String observation
) {}
