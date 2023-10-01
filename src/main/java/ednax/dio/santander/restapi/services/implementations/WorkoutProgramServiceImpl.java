package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.models.WorkoutModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.repositories.WorkoutProgramRepository;
import ednax.dio.santander.restapi.services.WorkoutProgramService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkoutProgramServiceImpl implements WorkoutProgramService {

    private final WorkoutProgramRepository workoutProgramRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public WorkoutProgramResponseDTO create(WorkoutProgramRequestDTO request) {
        WorkoutProgramModel workoutProgramToSave = modelMapper.map(request, WorkoutProgramModel.class);

        TeacherModel teacher = teacherRepository.findById(UUID.fromString(request.getTeacherId()))
                .orElseThrow(() -> new IllegalArgumentException("The teacher with the specified Id does not exists."));
        UserModel user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("The user with the specified Id does not exists."));

        // TODO: Alter this when implementing auth
        workoutProgramToSave.setTeacher(teacher);
        workoutProgramToSave.setUser(user);

        WorkoutProgramModel savedWorkoutProgram = workoutProgramRepository.save(workoutProgramToSave);

        WorkoutProgramResponseDTO response = modelMapper.map(savedWorkoutProgram, WorkoutProgramResponseDTO.class);

        // Returning Workouts as Empty Array
        response.setWorkouts(new ArrayList<>());

        return response;
    }

    @Override
    public void delete(String id) {
        var uuid = UUID.fromString(id);

        if(!workoutProgramRepository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("The Workout Program with id %s does not exists.", uuid));

        workoutProgramRepository.deleteById(uuid);
    }

    @Override
    public List<WorkoutProgramResponseDTO> findAll() {
        List<WorkoutProgramModel> workoutProgramModels = workoutProgramRepository.findAll();
        List<WorkoutProgramResponseDTO> response = new ArrayList<>();

        workoutProgramModels.forEach(workoutProgram -> {
            WorkoutProgramResponseDTO responseWorkoutProgram = modelMapper.map(workoutProgram, WorkoutProgramResponseDTO.class);

            setWorkoutsIntoResponseBody(workoutProgram, responseWorkoutProgram);

            response.add(responseWorkoutProgram);
        });

        return response;
    }

    @Override
    public WorkoutProgramResponseDTO findById(String id) {
        var uuid = UUID.fromString(id);

        WorkoutProgramModel workoutProgram = workoutProgramRepository.findById(uuid).orElseThrow(
            () -> new IllegalArgumentException("The workout program with the specified Id does not exists."));

        WorkoutProgramResponseDTO response = modelMapper.map(workoutProgram, WorkoutProgramResponseDTO.class);

        setWorkoutsIntoResponseBody(workoutProgram, response);

        return response;
    }

    // Todo: set workouts into updated entity

    @Override
    public WorkoutProgramResponseDTO update(String id, WorkoutProgramRequestDTO request) {
        var uuid = UUID.fromString(id);

        if(!workoutProgramRepository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("The Workout Program with id %s does not exists.", uuid));

        WorkoutProgramModel workoutProgramToModify = modelMapper.map(request, WorkoutProgramModel.class);
        workoutProgramToModify.setId(uuid);
        WorkoutProgramModel modifiedWorkoutProgram = workoutProgramRepository.save(workoutProgramToModify);

        WorkoutProgramResponseDTO response = modelMapper.map(modifiedWorkoutProgram, WorkoutProgramResponseDTO.class);
        
        setWorkoutsIntoResponseBody(modifiedWorkoutProgram, response);

        return response;
    }
    
    @Override
    public List<WorkoutResponseDTO> findProgramsWorkouts(String id) {
        var uuid = UUID.fromString(id);

        WorkoutProgramModel workoutProgram = workoutProgramRepository.findById(uuid).orElseThrow(
            () -> new IllegalArgumentException("The workout program with the specified Id does not exists."));

        List<WorkoutModel> workouts = workoutProgram.getWorkouts();
        List<WorkoutResponseDTO> response = new ArrayList<>();

        workouts.forEach(
            workout -> response.add(modelMapper.map(workout, WorkoutResponseDTO.class))
            );
            
        return response;
    }

    private void setWorkoutsIntoResponseBody(WorkoutProgramModel workoutProgram, WorkoutProgramResponseDTO response) {
        if(!(workoutProgram.getWorkouts() == null)) response.setWorkouts(new ArrayList<>(workoutProgram.getWorkouts())); 
        else response.setWorkouts(new ArrayList<>());
    }

}
