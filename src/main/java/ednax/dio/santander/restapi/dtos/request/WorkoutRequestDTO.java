package ednax.dio.santander.restapi.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record WorkoutRequestDTO(@NotBlank String name) {}
