package ednax.dio.santander.restapi.services;

import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private TeacherRepository teacherRepository;


    @Test
    public void shouldLoadUserByUsername() {
        Mockito.when(userRepository.findByLogin("login")).thenReturn(new UserModel());

        Assertions.assertEquals(new UserModel(), authService.loadUserByUsername("login"));
    }

    @Test
    public void shouldLoadTeacherByUsername() {
        Mockito.when(userRepository.findByLogin("login")).thenReturn(null);
        Mockito.when(teacherRepository.findByLogin("login")).thenReturn(new TeacherModel());

        Assertions.assertEquals(new TeacherModel(), authService.loadUserByUsername("login"));
    }

    @Test
    public void shouldReturnNullWhenLoginDoesNotExists() {
        Mockito.when(userRepository.findByLogin("login")).thenReturn(null);
        Mockito.when(teacherRepository.findByLogin("login")).thenReturn(null);

        Assertions.assertNull(authService.loadUserByUsername("login"));
    }

}
