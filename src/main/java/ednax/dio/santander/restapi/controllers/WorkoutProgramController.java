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

import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.services.WorkoutProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workout-programs")
@RequiredArgsConstructor
public class WorkoutProgramController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final WorkoutProgramService workoutProgramService;

    @GetMapping
    ResponseEntity<List<WorkoutProgramResponseDTO>> getAll() {
        var response = workoutProgramService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<WorkoutProgramResponseDTO> getById(@PathVariable String id) {
        var response = workoutProgramService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<WorkoutProgramResponseDTO> create(@RequestBody @Valid WorkoutProgramRequestDTO request) {
        var response = workoutProgramService.create(request);
        logger.info(String.format("Workout Program %s created.", response.getId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        workoutProgramService.delete(id);
        logger.info(String.format("Workout Program %s deleted", id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<WorkoutProgramResponseDTO> update(@PathVariable String id, @RequestBody @Valid WorkoutProgramRequestDTO request) {
        var response = workoutProgramService.update(id, request);
        logger.info(String.format("Workout Program %s updated.", id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/workouts")
    ResponseEntity<List<WorkoutResponseDTO>> getProgramsWorkouts(@PathVariable String id) {
        var response = workoutProgramService.findProgramsWorkouts(id);
        return ResponseEntity.ok(response);
    }
    
}
