package ednax.dio.santander.restapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTests {

    @InjectMocks
    private TokenService tokenService;

    private String expectedToken;
    private String expectedSubject;
    UserModel user = new UserModel();
    TeacherModel teacher = new TeacherModel();

    @BeforeEach
    public void setUpTokens() {
        ReflectionTestUtils.setField(tokenService, "secret", "secret");

        user.setLogin("login");
        teacher.setLogin("login");

        var algorithm = Algorithm.HMAC256("secret");

        expectedToken = JWT.create()
                .withIssuer("auth-api")
                .withSubject("login")
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .sign(algorithm);

        expectedSubject = JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(expectedToken)
                .getSubject();
    }

    @Test
    public void shouldGenerateUserToken() {
        String token = tokenService.generateUserToken(user);

        Assertions.assertEquals(expectedToken, token);
    }

    @Test
    public void shouldGenerateTeacherToken() {
        String token = tokenService.generateTeacherToken(teacher);

        Assertions.assertEquals(expectedToken, token);
    }

    @Test
    public void shouldValidateTokenAndReturnSubject() {
        String subject = tokenService.validateToken(expectedToken);

        Assertions.assertEquals(expectedSubject, subject);
    }

    @Test
    public void shouldValidateEmptyTokenAndReturnEmptyString() {
        String subject = tokenService.validateToken("");

        Assertions.assertEquals("", subject);
    }

}
