package ednax.dio.santander.restapi.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record TeacherRequestDTO(
    @NotBlank String firstName,
    @NotBlank String surname,
    @NotBlank String username,
    @NotBlank String password
) {}
