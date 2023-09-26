package ednax.dio.santander.restapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.services.WorkoutService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

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

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        workoutService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<WorkoutResponseDTO> update(@PathVariable String id, @RequestBody WorkoutRequestDTO request) {
        var response = workoutService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/exercises")
    ResponseEntity<List<ExerciseResponseDTO>> getProgramsWorkouts(@PathVariable String id) {
        var response = workoutService.findWorkoutsExercises(id);
        return ResponseEntity.ok(response);
    }
    
}
