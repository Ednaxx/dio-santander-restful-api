package ednax.dio.santander.restapi.services;

import java.util.List;

import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;

public interface UserService {

    List<UserResponseDTO> findAll();

    UserResponseDTO findById(String id);
    
    UserResponseDTO create(UserRequestDTO user);

    UserResponseDTO update(String id, UserRequestDTO user);

    void delete(String id);

}
