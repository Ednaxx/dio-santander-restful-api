package ednax.dio.santander.restapi.services;

import java.util.List;

import ednax.dio.santander.restapi.dtos.request.ExerciseRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;

public interface ExerciseService {

    List<ExerciseResponseDTO> findAll();

    ExerciseResponseDTO findById(String id);

    ExerciseResponseDTO create(String workoutId, ExerciseRequestDTO request);

    ExerciseResponseDTO update(String id, ExerciseRequestDTO request);

    void delete(String id);

}
