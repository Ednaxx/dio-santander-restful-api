package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.repositories.WorkoutProgramRepository;
import ednax.dio.santander.restapi.services.implementations.WorkoutProgramServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class WorkoutProgramServiceTests {

    @InjectMocks
    private WorkoutProgramServiceImpl workoutProgramService;
    @Mock
    private WorkoutProgramRepository workoutProgramRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper;


    @Test
    public void shouldCreateWorkoutProgram() {

    }

    @Test
    public void shouldThrowExceptionWhenProgramsUserDoesNotExists_onCreateWorkoutProgram() {

    }



    @Test
    public void shouldDeleteWorkoutProgram() {

    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onDeleteWorkoutProgram() {

    }



    @Test
    public void shouldFindAllWorkoutPrograms() {

    }



    @Test
    public void shouldFindWorkoutProgramById() {

    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onFindWorkoutProgramById() {

    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotValid() {
        String id = "";

        Assertions.assertThrows(RestException.class, () -> workoutProgramService.findById(id));
    }



    @Test
    public void shouldUpdateWorkoutProgram() {

    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onUpdateWorkoutProgram() {

    }

}
