package ednax.dio.santander.restapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ednax.dio.santander.restapi.models.WorkoutProgramModel;

public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgramModel, UUID> {
    
}
