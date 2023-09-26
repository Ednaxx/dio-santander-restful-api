package ednax.dio.santander.restapi.services;

import java.util.List;

import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;

public interface WorkoutProgramService {
    
    List<WorkoutProgramResponseDTO> findAll();

    WorkoutProgramResponseDTO findById(String id);
    
    WorkoutProgramResponseDTO create(String useerId, WorkoutProgramRequestDTO request);

    WorkoutProgramResponseDTO update(String id, WorkoutProgramRequestDTO request);

    void delete(String id);

    List<WorkoutResponseDTO> findProgramsWorkouts(String id);

}
