package ednax.dio.santander.restapi.dtos.response;

import java.util.List;
import java.util.UUID;

import ednax.dio.santander.restapi.models.WorkoutModel;

public record WorkoutProgramResponseDTO(
    UUID id,
    String name,
    String objective,
    String userName,
    String teacherName,
    List<WorkoutModel> workouts
) {}
