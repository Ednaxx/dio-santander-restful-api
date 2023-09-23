package ednax.dio.santander.restapi.dtos.response;

import java.util.List;

import ednax.dio.santander.restapi.models.WorkoutModel;

public record WorkoutProgramResponseDTO(
    String name,
    String objective,
    String userName,
    String teacherName,
    List<WorkoutModel> workouts
) {}
