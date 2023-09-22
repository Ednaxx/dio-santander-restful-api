package ednax.dio.santander.restapi.services;

import java.util.List;

import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;

public interface UserService {

    List<UserResponseDTO> findAll();

    UserResponseDTO findById(String id);
    
    UserResponseDTO create(UserRequestDTO request);

    UserResponseDTO update(String id, UserRequestDTO request);

    void delete(String id);

}
