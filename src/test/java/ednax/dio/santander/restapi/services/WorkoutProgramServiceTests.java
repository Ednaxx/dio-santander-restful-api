package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.repositories.WorkoutProgramRepository;
import ednax.dio.santander.restapi.services.implementations.WorkoutProgramServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Spy
    private ModelMapper modelMapper;


    @Test
    public void shouldCreateWorkoutProgram() {
        Mockito.when(workoutProgramRepository.save(Mockito.any(WorkoutProgramModel.class))).thenReturn(new WorkoutProgramModel());
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(new UserModel()));

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(teacherRepository.findByLogin(Mockito.any())).thenReturn(new TeacherModel());

        WorkoutProgramResponseDTO expected = new WorkoutProgramResponseDTO();

        WorkoutProgramRequestDTO request = new WorkoutProgramRequestDTO();
        request.setUser(UUID.randomUUID().toString());

        Assertions.assertEquals(expected, workoutProgramService.create(request));
    }

    @Test
    public void shouldThrowExceptionWhenProgramsUserDoesNotExists_onCreateWorkoutProgram() {
        var uuid = UUID.randomUUID();

        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        WorkoutProgramRequestDTO request = new WorkoutProgramRequestDTO();
        request.setUser(uuid.toString());

        Assertions.assertThrows(RestException.class, () -> workoutProgramService.create(request));
    }



    @Test
    public void shouldDeleteWorkoutProgram() {
        var uuid = UUID.randomUUID();

        Mockito.when(workoutProgramRepository.findById(uuid)).thenReturn(Optional.of(new WorkoutProgramModel()));

        workoutProgramService.delete(uuid.toString());
        Mockito.verify(workoutProgramRepository).deleteById(uuid);
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onDeleteWorkoutProgram() {
        var uuid = UUID.randomUUID();

        Mockito.when(workoutProgramRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> workoutProgramService.delete(uuid.toString()));
    }



    @Test
    public void shouldFindAllWorkoutPrograms() {
        Mockito.when(workoutProgramRepository.findAll()).thenReturn(List.of(new WorkoutProgramModel()));

        WorkoutProgramResponseDTO responseProgram = new WorkoutProgramResponseDTO();
        List<WorkoutProgramResponseDTO> expected = List.of(responseProgram);

        Assertions.assertEquals(expected, workoutProgramService.findAll());
    }



    @Test
    public void shouldFindWorkoutProgramById() {
        var uuid = UUID.randomUUID();

        Mockito.when(workoutProgramRepository.findById(uuid)).thenReturn(Optional.of(new WorkoutProgramModel()));

        Assertions.assertEquals(new WorkoutProgramResponseDTO(), workoutProgramService.findById(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onFindWorkoutProgramById() {
        var uuid = UUID.randomUUID();

        Mockito.when(workoutProgramRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> workoutProgramService.findById(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotValid() {
        String id = "";

        Assertions.assertThrows(RestException.class, () -> workoutProgramService.findById(id));
    }



    @Test
    public void shouldUpdateWorkoutProgram() {
        var uuid = UUID.randomUUID();
        WorkoutProgramModel workoutProgramModel = new WorkoutProgramModel();
        workoutProgramModel.setId(uuid);

        Mockito.when(workoutProgramRepository.findById(uuid)).thenReturn(Optional.of(workoutProgramModel));
        Mockito.when(workoutProgramRepository.save(workoutProgramModel)).thenReturn(workoutProgramModel);

        WorkoutProgramResponseDTO response = workoutProgramService.update(uuid.toString(), new WorkoutProgramRequestDTO());

        WorkoutProgramResponseDTO expected = new WorkoutProgramResponseDTO();
        expected.setId(uuid);

        Assertions.assertEquals(expected, response);
    }

    @Test
    public void shouldThrowExceptionWhenWorkoutProgramDoesNotExists_onUpdateWorkoutProgram() {
        var uuid = UUID.randomUUID();

        Mockito.when(workoutProgramRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> workoutProgramService.update(uuid.toString(), new WorkoutProgramRequestDTO()));
    }

}
