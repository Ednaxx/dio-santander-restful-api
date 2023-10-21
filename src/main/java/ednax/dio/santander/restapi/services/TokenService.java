package ednax.dio.santander.restapi.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;

@Service
public class TokenService {

    @Value("${token.secret}")
    private String secret;
    
    public String generateUserToken(UserModel user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.getLogin())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);

            return token;
        }
        catch (JWTCreationException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generating token");
        }
    }

    public String generateTeacherToken(TeacherModel teacher) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(teacher.getLogin())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
            
            return token;
        }
        catch (JWTCreationException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generating token");
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
        }
        catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
