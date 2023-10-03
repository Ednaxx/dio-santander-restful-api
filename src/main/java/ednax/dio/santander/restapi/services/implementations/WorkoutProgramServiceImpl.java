package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
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

        TeacherModel teacher = teacherRepository.findById(TeacherServiceImpl.validateTeacherId(request.getTeacher()))
                .orElseThrow(
                    () -> new RestException(HttpStatus.BAD_REQUEST, String.format("The Teacher with id %s does not exists.", request.getTeacher()))
                );
        UserModel user = userRepository.findById(UserServiceImpl.validateUserId(request.getUser()))
                .orElseThrow(
                    () -> new RestException(HttpStatus.BAD_REQUEST, String.format("The User with id %s does not exists.", request.getUser()))
                );

        // TODO: Alter this when implementing auth
        workoutProgramToSave.setTeacher(teacher);
        workoutProgramToSave.setUser(user);

        WorkoutProgramModel savedWorkoutProgram = workoutProgramRepository.save(workoutProgramToSave);

        return modelMapper.map(savedWorkoutProgram, WorkoutProgramResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        var uuid = validateWorkoutProgramId(id);

        if(!workoutProgramRepository.findById(uuid).isPresent()) throw new RestException(HttpStatus.NOT_FOUND, String.format("The Workout Program with id %s does not exists.", id));

        workoutProgramRepository.deleteById(uuid);
    }

    @Override
    public List<WorkoutProgramResponseDTO> findAll() {
        List<WorkoutProgramModel> workoutProgramModels = workoutProgramRepository.findAll();
        List<WorkoutProgramResponseDTO> response = new ArrayList<>();

        workoutProgramModels.forEach(workoutProgram -> {
            WorkoutProgramResponseDTO responseWorkoutProgram = modelMapper.map(workoutProgram, WorkoutProgramResponseDTO.class);
            response.add(responseWorkoutProgram);
        });

        return response;
    }

    @Override
    public WorkoutProgramResponseDTO findById(String id) {
        var uuid = validateWorkoutProgramId(id);

        WorkoutProgramModel workoutProgram = workoutProgramRepository.findById(uuid).orElseThrow(
            () -> new RestException(HttpStatus.NOT_FOUND, String.format("The Workout Program with id %s does not exists.", id)));

        return modelMapper.map(workoutProgram, WorkoutProgramResponseDTO.class);
    }

    // Todo: set workouts into updated entity

    @Override
    public WorkoutProgramResponseDTO update(String id, WorkoutProgramRequestDTO request) {
        var uuid = validateWorkoutProgramId(id);

        WorkoutProgramModel oldWorkoutProgram = workoutProgramRepository.findById(uuid).orElseThrow(
            () -> new RestException(HttpStatus.BAD_REQUEST, String.format("The Workout Program with id %s does not exists.", id))
        );

        WorkoutProgramModel workoutProgramToModify = modelMapper.map(request, WorkoutProgramModel.class);
        workoutProgramToModify.setId(uuid);
        workoutProgramToModify.setTeacher(oldWorkoutProgram.getTeacher());
        workoutProgramToModify.setUser(oldWorkoutProgram.getUser());
        WorkoutProgramModel modifiedWorkoutProgram = workoutProgramRepository.save(workoutProgramToModify);

        return modelMapper.map(modifiedWorkoutProgram, WorkoutProgramResponseDTO.class);
    }
    
    @Override
    public List<WorkoutResponseDTO> findProgramsWorkouts(String id) {
        var uuid = validateWorkoutProgramId(id);

        WorkoutProgramModel workoutProgram = workoutProgramRepository.findById(uuid).orElseThrow(
            () -> new RestException(HttpStatus.NOT_FOUND, String.format("The Workout Program with id %s does not exists.", id)));

        List<WorkoutModel> workouts = workoutProgram.getWorkouts();
        List<WorkoutResponseDTO> response = new ArrayList<>();

        workouts.forEach(
            workout -> response.add(modelMapper.map(workout, WorkoutResponseDTO.class))
            );
            
        return response;
    }

    static UUID validateWorkoutProgramId(String id) {
        try {
            return UUID.fromString(id);
        }
        catch (Exception e) {
            throw new RestException(HttpStatus.BAD_REQUEST, String.format("%s is not a valid UUID", id));
        }
    }

}
