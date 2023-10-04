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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/user/login")
    public ResponseEntity userLogin(@RequestBody @Valid UserAuthDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), "");
        var auth = authenticationManager.authenticate(usernamePassword);
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teacher/login")
    public ResponseEntity teacherLogin(@RequestBody @Valid TeacherAuthDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

}
