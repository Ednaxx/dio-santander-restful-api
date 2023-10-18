package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.ExerciseModel;
import ednax.dio.santander.restapi.models.WorkoutModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.WorkoutProgramRepository;
import ednax.dio.santander.restapi.repositories.WorkoutRepository;
import ednax.dio.santander.restapi.services.implementations.WorkoutServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTests {

    @InjectMocks
    private WorkoutServiceImpl workoutService;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private WorkoutProgramRepository workoutProgramRepository;
    @Spy
    private ModelMapper modelMapper;


    @Test
    public void shouldCreateWorkout() {
        var workoutProgramId = UUID.randomUUID();

        WorkoutProgramModel workoutProgram = new WorkoutProgramModel();
        workoutProgram.setWorkouts(new ArrayList<>());

        Mockito.when(workoutRepository.save(Mockito.any(WorkoutModel.class))).thenReturn(new WorkoutModel());
        Mockito.when(workoutProgramRepository.findById(workoutProgramId)).thenReturn(Optional.of(workoutProgram));

        WorkoutResponseDTO expected = new WorkoutResponseDTO();
        expected.setExercises(new ArrayList<>());

        WorkoutRequestDTO request = new WorkoutRequestDTO();
        request.setWorkoutProgram(workoutProgramId.toString());

        Assertions.assertEquals(expected, workoutService.create(request));
        Mockito.verify(workoutRepository).save(new WorkoutModel());
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onCreateWorkout() {
        var workoutProgramId = UUID.randomUUID();

        Mockito.when(workoutProgramRepository.findById(workoutProgramId)).thenReturn(Optional.empty());

        WorkoutRequestDTO request = new WorkoutRequestDTO();
        request.setWorkoutProgram(workoutProgramId.toString());

        Assertions.assertThrows(RestException.class, () -> workoutService.create(request));
    }



    @Test
    public void shouldDeleteWorkout() {
        Long id = 1L;

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.of(new WorkoutModel()));

        workoutService.delete(Long.toString(id));
        Mockito.verify(workoutRepository).deleteById(id);
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutDoesNotExists_onDeleteWorkout() {
        long id = 1L;

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> workoutService.delete(Long.toString(id)));
    }



    @Test
    public void shouldFindAllWorkouts() {
        List<WorkoutModel> workouts = List.of(new WorkoutModel());

        Mockito.when(workoutRepository.findAll()).thenReturn(workouts);

        WorkoutResponseDTO expectedWorkout = new WorkoutResponseDTO();
        expectedWorkout.setExercises(new ArrayList<>());

        Assertions.assertEquals(List.of(expectedWorkout), workoutService.findAll());
    }



    @Test
    public void shouldFindWorkoutById() {
        long id = 1L;

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.of(new WorkoutModel()));

        WorkoutResponseDTO expected = new WorkoutResponseDTO();
        expected.setExercises(new ArrayList<>());

        Assertions.assertEquals(expected, workoutService.findById(Long.toString(id)));
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutDoesNotExists_onFindWorkoutById() {
        long id = 1L;

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> workoutService.findById(Long.toString(id)));
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotValid() {
        String id = "";

        Assertions.assertThrows(RestException.class, () -> workoutService.findById(id));
    }



    @Test
    public void shouldUpdateWorkout() {
        Long id = 1L;

        WorkoutModel workout = new WorkoutModel();
        workout.setId(id);

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.of(workout));
        Mockito.when(workoutRepository.save(workout)).thenReturn(workout);

        WorkoutResponseDTO response = workoutService.update(Long.toString(id), new WorkoutRequestDTO());

        WorkoutResponseDTO expected = new WorkoutResponseDTO();
        expected.setExercises(new ArrayList<>());
        expected.setId(id);

        Assertions.assertEquals(expected, response);
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutDoesNotExists() {
        long id = 1L;

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> workoutService.update(Long.toString(id), new WorkoutRequestDTO()));
    }



    @Test
    public void shouldFindWorkoutsExercises() {
        long id = 1L;

        WorkoutModel workout = new WorkoutModel();
        workout.setExercises(List.of(new ExerciseModel()));

        Mockito.when(workoutRepository.findById(id)).thenReturn(Optional.of(workout));

        List<ExerciseResponseDTO> expected = List.of(new ExerciseResponseDTO());

        Assertions.assertEquals(expected, workoutService.findWorkoutsExercises(Long.toString(id)));
    }


}
