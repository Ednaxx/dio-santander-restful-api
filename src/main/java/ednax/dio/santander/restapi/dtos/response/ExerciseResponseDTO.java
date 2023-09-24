package ednax.dio.santander.restapi.dtos.response;

public record ExerciseResponseDTO(
    Long id,
    String name,
    String duration,
    String observation
) {}
