package ednax.dio.santander.restapi.controllers;

import ednax.dio.santander.restapi.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ednax.dio.santander.restapi.dtos.request.TeacherAuthDTO;
import ednax.dio.santander.restapi.dtos.request.UserAuthDTO;
import ednax.dio.santander.restapi.dtos.response.AuthResponseDTO;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/user/login")
    public ResponseEntity<AuthResponseDTO> userLogin(@RequestBody @Valid UserAuthDTO request) {
        logger.info("Trying user auth...");

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), "user");

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateUserToken((UserModel) auth.getPrincipal());

        logger.info(request.login() + " authenticated.");
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<AuthResponseDTO> teacherLogin(@RequestBody @Valid TeacherAuthDTO request) {
        logger.info("Trying teacher auth...");

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateTeacherToken((TeacherModel) auth.getPrincipal());

        logger.info(request.login() + " authenticated.");
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

}
