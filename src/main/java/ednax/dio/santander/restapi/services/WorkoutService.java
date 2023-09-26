package ednax.dio.santander.restapi.services;

import java.util.List;

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;

public interface WorkoutService {
    
    List<WorkoutResponseDTO> findAll();

    WorkoutResponseDTO findById(String id);

    WorkoutResponseDTO create(String programId, WorkoutRequestDTO request);

    WorkoutResponseDTO update(String id, WorkoutRequestDTO request);

    void delete(String id);

    List<ExerciseResponseDTO> findWorkoutsExercises(String id);

}
