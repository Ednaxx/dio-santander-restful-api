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

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final WorkoutService workoutService;

    @GetMapping
    ResponseEntity<List<WorkoutResponseDTO>> getAll() {
        var response = workoutService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<WorkoutResponseDTO> getById(@PathVariable String id) {
        var response = workoutService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<WorkoutResponseDTO> create(@RequestBody @Valid WorkoutRequestDTO request) {
        var response = workoutService.create(request);
        logger.info(String.format("Workout %s created.", response.getId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        workoutService.delete(id);
        logger.info(String.format("Workout %s deleted", id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<WorkoutResponseDTO> update(@PathVariable String id, @RequestBody @Valid WorkoutRequestDTO request) {
        var response = workoutService.update(id, request);
        logger.info(String.format("Workout %s updated.", id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/exercises")
    ResponseEntity<List<ExerciseResponseDTO>> getWorkoutsExercises(@PathVariable String id) {
        var response = workoutService.findWorkoutsExercises(id);
        return ResponseEntity.ok(response);
    }
    
}
