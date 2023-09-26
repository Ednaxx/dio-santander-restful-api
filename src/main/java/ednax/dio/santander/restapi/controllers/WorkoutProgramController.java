package ednax.dio.santander.restapi.controllers;

import java.util.List;

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
import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.services.WorkoutProgramService;
import ednax.dio.santander.restapi.services.WorkoutService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workout-programs")
@RequiredArgsConstructor
public class WorkoutProgramController {

    private final WorkoutProgramService workoutProgramService;
    private final WorkoutService workoutService;

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

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        workoutProgramService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<WorkoutProgramResponseDTO> update(@PathVariable String id, @RequestBody WorkoutProgramRequestDTO request) {
        var response = workoutProgramService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/workouts")
    ResponseEntity<List<WorkoutResponseDTO>> getProgramsWorkouts(@PathVariable String id) {
        var response = workoutProgramService.findProgramsWorkouts(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/workouts")
    ResponseEntity<WorkoutResponseDTO> createProgramsWorkout(@PathVariable String id, @RequestBody WorkoutRequestDTO request) {
        var response = workoutService.create(id, request);
        return ResponseEntity.ok(response);
    }
    
}
