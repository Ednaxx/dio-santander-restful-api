package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
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
    public WorkoutResponseDTO create(WorkoutRequestDTO request) {
        WorkoutModel workoutToSave = modelMapper.map(request, WorkoutModel.class);

        WorkoutProgramModel workoutProgram = workoutProgramRepository.findById(WorkoutProgramServiceImpl.validateWorkoutProgramId(request.getWorkoutProgram()))
            .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The Teacher with id %s does not exists.", request.getWorkoutProgram()))
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
        var longId = validateWorkoutId(id);

        if(!repository.findById(longId).isPresent()) throw new RestException(HttpStatus.CONFLICT, "A Workout Program with this login already exists.");
        
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
        var longId = validateWorkoutId(id);

        WorkoutModel workout = repository.findById(longId).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The Workout Program with id %s does not exists.", id)));

        WorkoutResponseDTO response = modelMapper.map(workout, WorkoutResponseDTO.class);
        setExercisesIntoResponseBody(workout, response);

        return response;
    }

    @Override
    public WorkoutResponseDTO update(String id, WorkoutRequestDTO request) {
        var longId = validateWorkoutId(id);

        if(!repository.findById(longId).isPresent()) throw new RestException(HttpStatus.NOT_FOUND, String.format("The Workout Program with id %s does not exists.", id));

        WorkoutModel workoutToModify = modelMapper.map(request, WorkoutModel.class);
        workoutToModify.setId(longId);
        WorkoutModel modifiedWorkout = repository.save(workoutToModify);

        WorkoutResponseDTO respose = modelMapper.map(modifiedWorkout, WorkoutResponseDTO.class);
        setExercisesIntoResponseBody(modifiedWorkout, respose);

        return respose;
    }
    
    public List<ExerciseResponseDTO> findWorkoutsExercises(String id) {
        var longId = validateWorkoutId(id);

        WorkoutModel workout = repository.findById(longId).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The Workout Program with id %s does not exists.", id)));

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

    static Long validateWorkoutId(String id) {
        try {
            return Long.parseLong(id);
        }
        catch (Exception e) {
            throw new RestException(HttpStatus.BAD_REQUEST, String.format("%s is not a valid Long", id));
        }
    }

}
