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

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.services.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    ResponseEntity<List<TeacherResponseDTO>> getAll() {
        var response = teacherService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<TeacherResponseDTO> getById(@PathVariable String id) {
        var response = teacherService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<TeacherResponseDTO> create(@RequestBody @Valid TeacherRequestDTO request) {
        var response = teacherService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id) {
        teacherService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<TeacherResponseDTO> update(@PathVariable String id, @RequestBody TeacherRequestDTO request) {
        var response = teacherService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/workout-programs")
    ResponseEntity<List<WorkoutProgramResponseDTO>> getTeachersWorkoutPrograms(@PathVariable String id) {
        var response = teacherService.findTeachersWorkoutPrograms(id);
        return ResponseEntity.ok(response);
    }
    
}
