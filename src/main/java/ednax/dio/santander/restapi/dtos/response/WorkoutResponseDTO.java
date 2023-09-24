package ednax.dio.santander.restapi.dtos.response;

import java.util.List;

import ednax.dio.santander.restapi.models.ExerciseModel;

public record WorkoutResponseDTO(Long id, String name, List<ExerciseModel> exercises) {}
