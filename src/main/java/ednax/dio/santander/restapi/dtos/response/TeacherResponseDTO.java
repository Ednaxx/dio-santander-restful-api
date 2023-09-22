package ednax.dio.santander.restapi.dtos.response;

import jakarta.validation.constraints.NotBlank;

public record TeacherResponseDTO(
    @NotBlank String firstName,
    @NotBlank String surname,
    @NotBlank String username
) {}
