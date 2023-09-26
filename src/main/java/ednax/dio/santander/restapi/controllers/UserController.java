package ednax.dio.santander.restapi.controllers;

import java.util.List;

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
import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.services.UserService;
import ednax.dio.santander.restapi.services.WorkoutProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WorkoutProgramService workoutProgramService;
    
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
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponseDTO> update(@PathVariable String id, @RequestBody UserRequestDTO request) {
        var response = userService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/workout-programs")
    ResponseEntity<List<WorkoutProgramResponseDTO>> getUsersWorkoutPrograms(@PathVariable String id) {
        var response = userService.findUsersWorkoutPrograms(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/workout-programs")
    ResponseEntity<WorkoutProgramResponseDTO> createUsersWorkoutProgram(@PathVariable String id, @RequestBody WorkoutProgramRequestDTO request) {
        var response = workoutProgramService.create(id, request);
        return ResponseEntity.ok(response);
    }

}
