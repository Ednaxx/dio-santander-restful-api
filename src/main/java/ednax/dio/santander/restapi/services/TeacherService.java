package ednax.dio.santander.restapi.services;

import java.util.List;

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;

public interface TeacherService {
    
    List<TeacherResponseDTO> findAll();

    TeacherResponseDTO findById(String id);
    
    TeacherResponseDTO create(TeacherRequestDTO user);

    TeacherResponseDTO update(String id, TeacherRequestDTO user);

    void delete(String id);

}
