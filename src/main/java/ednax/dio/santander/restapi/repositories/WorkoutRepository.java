package ednax.dio.santander.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ednax.dio.santander.restapi.models.WorkoutModel;

public interface WorkoutRepository extends JpaRepository<WorkoutModel, Long> {
    
}
