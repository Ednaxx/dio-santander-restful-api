package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.models.ExerciseModel;
import ednax.dio.santander.restapi.models.WorkoutModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.WorkoutProgramRepository;
import ednax.dio.santander.restapi.repositories.WorkoutRepository;
import ednax.dio.santander.restapi.services.WorkoutService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository repository;
    private final WorkoutProgramRepository workoutProgramRepository;
    private final ModelMapper modelMapper;

    @Override
    public WorkoutResponseDTO create(String programId, WorkoutRequestDTO request) {
        WorkoutModel workoutToSave = modelMapper.map(request, WorkoutModel.class);

        WorkoutProgramModel workoutProgram = workoutProgramRepository.findById(UUID.fromString(programId))
            .orElseThrow(() -> new IllegalArgumentException("The workout program with the specified Id does not exists.")
        );

        WorkoutModel savedWorkout = repository.save(workoutToSave);
        workoutProgram.getWorkouts().add(savedWorkout);
        workoutProgramRepository.save(workoutProgram);

        WorkoutResponseDTO response = modelMapper.map(savedWorkout, WorkoutResponseDTO.class);

        setExercisesIntoResponseBody(savedWorkout, response);

        return response;
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
            WorkoutResponseDTO workoutResponse = modelMapper.map(workout, WorkoutResponseDTO.class);
            setExercisesIntoResponseBody(workout, workoutResponse);

            response.add(workoutResponse);
        });

        return response;
    }

    @Override
    public WorkoutResponseDTO findById(String id) {
        var longId = Long.parseLong(id);

        WorkoutModel workout = repository.findById(longId).orElseThrow(() -> new IllegalArgumentException("The workout with the specified Id does not exists."));

        WorkoutResponseDTO response = modelMapper.map(workout, WorkoutResponseDTO.class);
        setExercisesIntoResponseBody(workout, response);

        return response;
    }

    @Override
    public WorkoutResponseDTO update(String id, WorkoutRequestDTO request) {
        var longId = Long.parseLong(id);

        if(!repository.findById(longId).isPresent()) throw new IllegalArgumentException(String.format("The Workout with id %s does not exists.", longId));

        WorkoutModel workoutToModify = modelMapper.map(request, WorkoutModel.class);
        WorkoutModel modifiedWorkout = repository.save(workoutToModify);

        WorkoutResponseDTO respose = modelMapper.map(modifiedWorkout, WorkoutResponseDTO.class);
        setExercisesIntoResponseBody(modifiedWorkout, respose);

        return respose;
    }
    
    public List<ExerciseResponseDTO> findWorkoutsExercises(String id) {
        var longId = Long.parseLong(id);

        WorkoutModel workout = repository.findById(longId).orElseThrow(() -> new IllegalArgumentException("The workout with the specified Id does not exists."));

        List<ExerciseModel> exercises = workout.getExercises();
        List<ExerciseResponseDTO> response = new ArrayList<>();

        exercises.forEach(
            exercise -> response.add(modelMapper.map(exercise, ExerciseResponseDTO.class))
            );
            
        return response;
    }
    

    private void setExercisesIntoResponseBody(WorkoutModel workout, WorkoutResponseDTO response) {
        if(!(workout.getExercises() == null)) response.setExercises(new ArrayList<>(workout.getExercises())); 
        else response.setExercises(new ArrayList<>());
    }

}
