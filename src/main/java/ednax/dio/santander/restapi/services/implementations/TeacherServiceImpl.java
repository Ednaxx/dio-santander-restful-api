package ednax.dio.santander.restapi.services.implementations;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.services.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Override
    public TeacherResponseDTO create(TeacherRequestDTO user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<TeacherResponseDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeacherResponseDTO findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeacherResponseDTO update(String id, TeacherRequestDTO user) {
        // TODO Auto-generated method stub
        return null;
    }
    
    

}
