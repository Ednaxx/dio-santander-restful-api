package ednax.dio.santander.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ednax.dio.santander.restapi.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
    
}
