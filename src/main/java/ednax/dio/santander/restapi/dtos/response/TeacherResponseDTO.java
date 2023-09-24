package ednax.dio.santander.restapi.dtos.response;

import java.util.List;
import java.util.UUID;

import ednax.dio.santander.restapi.models.WorkoutProgramModel;

public record TeacherResponseDTO(
    UUID id,
    String firstName,
    String surname,
    String username,
    List<WorkoutProgramModel> workouts
) {}
