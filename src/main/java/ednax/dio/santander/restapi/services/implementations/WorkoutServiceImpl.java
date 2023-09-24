package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.models.WorkoutModel;
import ednax.dio.santander.restapi.repositories.WorkoutRepository;
import ednax.dio.santander.restapi.services.WorkoutService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public WorkoutResponseDTO create(WorkoutRequestDTO request) {
        WorkoutModel workoutToSave = modelMapper.map(request, WorkoutModel.class);
        WorkoutModel savedWorkout = repository.save(workoutToSave);

        return modelMapper.map(savedWorkout, WorkoutResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        var longId = Long.parseLong(id);

        if(!repository.findById(longId).isPresent()) throw new IllegalArgumentException(String.format("The Workout with id %s does not exists.", longId));
        
        repository.deleteById(longId);
    }

    @Override
    public List<WorkoutResponseDTO> findAll() {
        List<WorkoutModel> workoutModels = repository.findAll();
        List<WorkoutResponseDTO> response = new ArrayList<>();

        workoutModels.forEach(workout -> {
            response.add(modelMapper.map(workout, WorkoutResponseDTO.class));
        });

        return response;
    }

    @Override
    public WorkoutResponseDTO findById(String id) {
        var longId = Long.parseLong(id);

        WorkoutModel workout = repository.findById(longId).orElseThrow(NoSuchElementException::new);

        return modelMapper.map(workout, WorkoutResponseDTO.class);
    }

    @Override
    public WorkoutResponseDTO update(String id, WorkoutRequestDTO request) {
        var longId = Long.parseLong(id);

        if(!repository.findById(longId).isPresent()) throw new IllegalArgumentException(String.format("The Workout with id %s does not exists.", longId));

        WorkoutModel workoutToModify = modelMapper.map(request, WorkoutModel.class);
        WorkoutModel modifiedWorkout = repository.save(workoutToModify);
        WorkoutResponseDTO respose = modelMapper.map(modifiedWorkout, WorkoutResponseDTO.class);

        return respose;
    }
    
    

}
