package ednax.dio.santander.restapi.controllers;

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

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/user/login")
    public ResponseEntity<AuthResponseDTO> userLogin(@RequestBody @Valid UserAuthDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), "");
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateUserToken((UserModel) auth.getPrincipal());
        
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<AuthResponseDTO> teacherLogin(@RequestBody @Valid TeacherAuthDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateTeacherToken((TeacherModel) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

}
