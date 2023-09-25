package ednax.dio.santander.restapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ednax.dio.santander.restapi.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByLogin(String login);
    
}
