package ednax.dio.santander.restapi.dtos.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import ednax.dio.santander.restapi.models.WorkoutProgramModel;

public record UserResponseDTO (
    UUID id,
    String firstName,
    String surname,
    String sex,
    Integer age,
    BigDecimal weight,
    BigDecimal height,
    List<WorkoutProgramModel> workouts
    ) {}
