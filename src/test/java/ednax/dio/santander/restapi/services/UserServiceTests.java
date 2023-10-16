package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.services.implementations.UserServiceImpl;
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
public class UserServiceTests {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Spy
    private ModelMapper modelMapper;


    @Test
    public void shouldCreateUser() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new UserModel());

        UserResponseDTO response = userService.create(new UserRequestDTO());

        UserResponseDTO expected = new UserResponseDTO();
        expected.setWorkoutPrograms(new ArrayList<>());

        Mockito.verify(userRepository).save(new UserModel());
        Assertions.assertEquals(expected, response);
    }

    @Test
    public void shouldThrowExceptionWhenUserLoginAlreadyExists_onCreateUser() {
        UserRequestDTO request = new UserRequestDTO();
        request.setLogin("TestLogin");

        Mockito.when(userRepository.findByLogin(request.getLogin())).thenReturn(new UserModel());

        Assertions.assertThrows(RestException.class, () -> userService.create(request));
    }

    @Test
    public void shouldThrowExceptionWhenTeacherLoginAlreadyExists_onCreateUser() {
        UserRequestDTO request = new UserRequestDTO();
        request.setLogin("TestLogin");

        Mockito.when(teacherRepository.findByLogin(request.getLogin())).thenReturn(new TeacherModel());

        Assertions.assertThrows(RestException.class, () -> userService.create(request));
    }



    @Test
    public void shouldDeleteUser() {
        var uuid = UUID.randomUUID();
        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.of(new UserModel()));

        userService.delete(uuid.toString());
        Mockito.verify(userRepository).deleteById(uuid);
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExists_onDeleteUser() {
        var uuid = UUID.randomUUID();
        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> userService.delete(uuid.toString()));
    }



    @Test
    public void shouldFindAllUsers() {
        UserModel user = new UserModel();
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        UserResponseDTO responseUser = new UserResponseDTO();
        responseUser.setWorkoutPrograms(new ArrayList<>());

        Assertions.assertEquals(List.of(responseUser), userService.findAll());
    }



    @Test
    public void shouldFindUserById() {
        var uuid = UUID.randomUUID();
        UserModel user = new UserModel();
        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        UserResponseDTO response = new UserResponseDTO();
        response.setWorkoutPrograms(new ArrayList<>());

        Assertions.assertEquals(response, userService.findById(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExists_onFindById() {
        var uuid = UUID.randomUUID();
        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> userService.findById(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotValid() {
        String id = "";

        Assertions.assertThrows(RestException.class, () -> userService.findById(id));
    }



    @Test
    public void shouldUpdateUser() {
        UserModel user = new UserModel();
        var uuid = UUID.randomUUID();
        user.setId(uuid);

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.update(uuid.toString(), new UserRequestDTO());

        UserResponseDTO expected = new UserResponseDTO();
        expected.setId(uuid);
        expected.setWorkoutPrograms(new ArrayList<>());

        Mockito.verify(userRepository).save(user);
        Assertions.assertEquals(expected, response);
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExists_onUpdateUser() {
        var uuid = UUID.randomUUID();

        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> userService.update(uuid.toString(), new UserRequestDTO()));
    }



    @Test
    public void shouldFindUsersWorkoutPrograms() {
        UserModel user = new UserModel();
        user.setWorkoutPrograms(List.of(new WorkoutProgramModel()));

        var uuid = UUID.randomUUID();
        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        List<WorkoutProgramResponseDTO> expected = List.of(new WorkoutProgramResponseDTO());

        Assertions.assertEquals(expected, userService.findUsersWorkoutPrograms(uuid.toString()));
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExists_onFindUsersWorkoutPrograms() {
        var uuid = UUID.randomUUID();

        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(RestException.class, () -> userService.findUsersWorkoutPrograms(uuid.toString()));
    }

}
