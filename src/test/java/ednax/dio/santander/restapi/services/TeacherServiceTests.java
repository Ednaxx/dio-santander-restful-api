package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.services.implementations.TeacherServiceImpl;
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
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTests {

    @InjectMocks
    private TeacherServiceImpl teacherService;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private UserRepository userRepository;
    @Spy
    private ModelMapper modelMapper;


    @Test
    public void shouldCreateTeacher() {
        TeacherRequestDTO request = new TeacherRequestDTO();
        request.setPassword("password");

        Mockito.when(teacherRepository.save(Mockito.any(TeacherModel.class))).thenReturn(new TeacherModel());

        TeacherResponseDTO response = teacherService.create(request);

        TeacherResponseDTO expected = new TeacherResponseDTO();
        expected.setWorkoutPrograms(new ArrayList<>());

        Assertions.assertEquals(expected, response);
    }

    @Test
    public void shouldThrowExceptionWhenTeacherLoginAlreadyExists() {
        TeacherRequestDTO request = new TeacherRequestDTO();
        request.setLogin("TestLogin");
        request.setPassword("password");

        Mockito.when(teacherRepository.findByLogin(request.getLogin())).thenReturn(new TeacherModel());

        Assertions.assertThrows(RestException.class, () -> teacherService.create(request));
    }

    @Test
    public void shouldThrowExceptionWhenUserLoginAlreadyExists() {
        TeacherRequestDTO request = new TeacherRequestDTO();
        request.setLogin("TestLogin");
        request.setPassword("password");

        Mockito.when(userRepository.findByLogin(request.getLogin())).thenReturn(new UserModel());

        Assertions.assertThrows(RestException.class, () -> teacherService.create(request));
    }



    @Test
    public void shouldDeleteTeacher() {
        var uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.of(new TeacherModel()));

        teacherService.delete(uuid.toString());
        Mockito.verify(teacherRepository).deleteById(uuid);
    }

    @Test
    public void shouldThrowExceptionWhenTeacherDoesNotExists_onDelete() {
        var uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> teacherService.delete(uuid.toString()));
    }



    @Test
    public void shouldFindAllTeachers() {
        TeacherModel teacher = new TeacherModel();
        teacher.setPassword("password");

        Mockito.when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        TeacherResponseDTO teacherResponse = new TeacherResponseDTO();
        teacherResponse.setWorkoutPrograms(new ArrayList<>());
        List<TeacherResponseDTO> expected = List.of(teacherResponse);

        Assertions.assertEquals(expected, teacherService.findAll());
    }



    @Test
    public void shouldFindTeacherById() {
        var uuid = UUID.randomUUID();

        TeacherModel teacher = new TeacherModel();
        teacher.setPassword("password");

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.of(teacher));

        TeacherResponseDTO expected = new TeacherResponseDTO();
        expected.setWorkoutPrograms(new ArrayList<>());

        Assertions.assertEquals(expected, teacherService.findById(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExists_onFindById() {
        var uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> teacherService.delete(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotValid() {
        String id = "";

        Assertions.assertThrows(RestException.class, () -> teacherService.findById(id));
    }



    @Test
    public void shouldUpdateTeacher() {
        var uuid = UUID.randomUUID();
        TeacherModel teacher = new TeacherModel();
        teacher.setId(uuid);

        Mockito.when(teacherRepository.save(teacher)).thenReturn(teacher);
        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.of(teacher));

        TeacherResponseDTO response = teacherService.update(uuid.toString(), new TeacherRequestDTO());

        TeacherResponseDTO expected = new TeacherResponseDTO();
        expected.setWorkoutPrograms(new ArrayList<>());
        expected.setId(uuid);

        Mockito.verify(teacherRepository).save(teacher);
        Assertions.assertEquals(expected, response);
    }

    @Test
    public void shouldThrowExceptionWhenTeacherDoesNotExists_onUpdate() {
        var uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> teacherService.update(uuid.toString(), new TeacherRequestDTO()));
    }



    @Test
    public void shouldFindTeachersWorkoutPrograms() {
        var uuid = UUID.randomUUID();

        TeacherModel teacher = new TeacherModel();
        teacher.setWorkoutPrograms(List.of(new WorkoutProgramModel()));

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.of(teacher));

        List<WorkoutProgramResponseDTO> expected = List.of(new WorkoutProgramResponseDTO());

        Assertions.assertEquals(expected, teacherService.findTeachersWorkoutPrograms(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenTeacherDoesNotExists_onFindWorkoutPrograms() {
        var uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> teacherService.findTeachersWorkoutPrograms(uuid.toString()));
    }

}
