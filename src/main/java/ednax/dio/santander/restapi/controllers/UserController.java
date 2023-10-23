package ednax.dio.santander.restapi.controllers;

import java.util.List;

import ednax.dio.santander.restapi.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final UserService userService;
    
    @GetMapping
    ResponseEntity<List<UserResponseDTO>> getAll() {
        var response = userService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getById(@PathVariable String id) {
        var response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO request) {
        var response = userService.create(request);
        logger.info(String.format("User %s created.", response.getId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        logger.info(String.format("User %s deleted", id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponseDTO> update(@PathVariable String id, @RequestBody @Valid UserRequestDTO request) {
        var response = userService.update(id, request);
        logger.info(String.format("User %s updated.", id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/workout-programs")
    ResponseEntity<List<WorkoutProgramResponseDTO>> getUsersWorkoutPrograms(@PathVariable String id) {
        var response = userService.findUsersWorkoutPrograms(id);
        return ResponseEntity.ok(response);
    }

}
