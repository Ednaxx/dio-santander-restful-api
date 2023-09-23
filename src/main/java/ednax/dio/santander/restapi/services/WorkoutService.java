package ednax.dio.santander.restapi.services;

import java.util.List;

public interface WorkoutService {
    
    List<WorkoutResponseDTO> findAll();

    WorkoutResponseDTO findById(String id);

    WorkoutResponseDTO create(WorkoutRequestDTO request);

    WorkoutResponseDTO update(String id, WorkoutRequestDTO request);

    void delete(String id);

}
