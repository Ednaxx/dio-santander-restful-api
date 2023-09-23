package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.WorkoutProgramRepository;
import ednax.dio.santander.restapi.services.WorkoutProgramService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkoutProgramServiceImpl implements WorkoutProgramService {

    private final WorkoutProgramRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public WorkoutProgramResponseDTO create(WorkoutProgramRequestDTO request) {
        WorkoutProgramModel workoutProgramToSave = modelMapper.map(request, WorkoutProgramModel.class);
        WorkoutProgramModel savedWorkoutProgram = repository.save(workoutProgramToSave);

        return modelMapper.map(savedWorkoutProgram, WorkoutProgramResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        var uuid = UUID.fromString(id);

        if(!repository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("The Workout Program with id %s does not exists.", uuid));

        repository.deleteById(uuid);
    }

    @Override
    public List<WorkoutProgramResponseDTO> findAll() {
        List<WorkoutProgramModel> workoutProgramModels = repository.findAll();
        List<WorkoutProgramResponseDTO> response = new ArrayList<>();

        workoutProgramModels.forEach(workoutProgram -> {
            response.add(modelMapper.map(workoutProgram, WorkoutProgramResponseDTO.class));
        });

        return response;
    }

    @Override
    public WorkoutProgramResponseDTO findById(String id) {
        var uuid = UUID.fromString(id);

        WorkoutProgramModel workoutProgramModel = repository.findById(uuid).orElseThrow(NoSuchElementException::new);
        WorkoutProgramResponseDTO response = modelMapper.map(workoutProgramModel, WorkoutProgramResponseDTO.class);

        return response;
    }

    @Override
    public WorkoutProgramResponseDTO update(String id, WorkoutProgramRequestDTO request) {
        var uuid = UUID.fromString(id);

        if(!repository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("The Workout Program with id %s does not exists.", uuid));

        WorkoutProgramModel workoutProgramToModify = modelMapper.map(request, WorkoutProgramModel.class);
        workoutProgramToModify.setId(uuid);
        WorkoutProgramModel modifiedWorkoutProgram = repository.save(workoutProgramToModify);

        return modelMapper.map(modifiedWorkoutProgram, WorkoutProgramResponseDTO.class);
    }
    

}
