package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.dtos.request.ExerciseRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.ExerciseModel;
import ednax.dio.santander.restapi.models.WorkoutModel;
import ednax.dio.santander.restapi.repositories.ExerciseRepository;
import ednax.dio.santander.restapi.repositories.WorkoutRepository;
import ednax.dio.santander.restapi.services.implementations.ExerciseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTests {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Spy
    private ModelMapper modelMapper;


    @Test
    public void shouldCreateExercise() {
        long workoutId = 1L;

        WorkoutModel workout = new WorkoutModel();
        workout.setExercises(new ArrayList<>());

        Mockito.when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        Mockito.when(exerciseRepository.save(Mockito.any(ExerciseModel.class))).thenReturn(new ExerciseModel());

        ExerciseRequestDTO request = new ExerciseRequestDTO();
        request.setWorkout(Long.toString(workoutId));

        Assertions.assertEquals(new ExerciseResponseDTO(), exerciseService.create(request));
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutDoesNotExists_onCreateExercise() {
        long workoutId = 1L;

        Mockito.when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        ExerciseRequestDTO request = new ExerciseRequestDTO();
        request.setWorkout(Long.toString(workoutId));

        Assertions.assertThrows(RestException.class, () -> exerciseService.create(request));
    }



    @Test
    public void shouldDeleteExercise() {
        long id = 1L;

        Mockito.when(exerciseRepository.findById(id)).thenReturn(Optional.of(new ExerciseModel()));

        exerciseService.delete(Long.toString(id));
        Mockito.verify(exerciseRepository).deleteById(id);
    }

    @Test
    public void shouldThrowExceptionWhenExerciseDoesNotExists_onDeleteExercise() {
        long id = 1L;

        Mockito.when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> exerciseService.delete(Long.toString(id)));
    }



    @Test
    public void shouldFindAllExercises() {
        List<ExerciseModel> exercises = List.of(new ExerciseModel());

        Mockito.when(exerciseRepository.findAll()).thenReturn(exercises);

        List<ExerciseResponseDTO> expected = List.of(new ExerciseResponseDTO());

        Assertions.assertEquals(expected, exerciseService.findAll());
    }



    @Test
    public void shouldFindExerciseById() {
        long id = 1L;

        Mockito.when(exerciseRepository.findById(id)).thenReturn(Optional.of(new ExerciseModel()));

        Assertions.assertEquals(new ExerciseResponseDTO(), exerciseService.findById(Long.toString(id)));
    }

    @Test
    public void shouldThrowExceptionWhenExerciseDoesNotExists_onFindExerciseById() {
        long id = 1L;

        Mockito.when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> exerciseService.findById(Long.toString(id)));
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotValid() {
        String id = "";

        Assertions.assertThrows(RestException.class, () -> exerciseService.findById(id));
    }



    @Test
    public void shouldUpdateExercise() {
        long id = 1L;

        ExerciseModel exercise = new ExerciseModel();
        exercise.setId(id);

        Mockito.when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));
        Mockito.when(exerciseRepository.save(exercise)).thenReturn(exercise);

        ExerciseResponseDTO expected = new ExerciseResponseDTO();
        expected.setId(id);

        Assertions.assertEquals(expected, exerciseService.update(Long.toString(id), new ExerciseRequestDTO()));
    }

    @Test
    public void shouldThrowExceptionWhenExerciseDoesNotExists() {
        long id = 1L;

        Mockito.when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> exerciseService.update(Long.toString(id), new ExerciseRequestDTO()));
    }


}
