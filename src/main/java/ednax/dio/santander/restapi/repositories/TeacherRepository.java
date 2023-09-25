package ednax.dio.santander.restapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ednax.dio.santander.restapi.models.TeacherModel;

public interface TeacherRepository extends JpaRepository<TeacherModel, UUID> {

    TeacherModel findByUsername(String username);
    
}
