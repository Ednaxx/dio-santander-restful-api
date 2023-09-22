package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.services.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Override
    public TeacherResponseDTO create(TeacherRequestDTO request) {
        TeacherModel teacherToSave = modelMapper.map(request, TeacherModel.class);

        if (teacherRepository.findById(teacherToSave.getId()).isPresent()) throw new IllegalArgumentException("This teacher already exists.");

        TeacherModel savedTeacher = teacherRepository.save(teacherToSave);

        return modelMapper.map(savedTeacher, TeacherResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        var uuid = UUID.fromString(id);

        if(!teacherRepository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("This teacher with id %s does not exists.", uuid));

        teacherRepository.deleteById(uuid);
    }

    @Override
    public List<TeacherResponseDTO> findAll() {
        List<TeacherModel> teacherModels = teacherRepository.findAll();
        List<TeacherResponseDTO> response = new ArrayList<>();

        teacherModels.forEach(teacher -> {
            response.add(modelMapper.map(teacher, TeacherResponseDTO.class));
        });

        return response;
    }

    @Override
    public TeacherResponseDTO findById(String id) {
        var uuid = UUID.fromString(id);
        TeacherModel teacher = teacherRepository.findById(uuid).orElseThrow(NoSuchElementException::new);

        return modelMapper.map(teacher, TeacherResponseDTO.class);
    }

    @Override
    public TeacherResponseDTO update(String id, TeacherRequestDTO request) {
        var uuid = UUID.fromString(id);

        if(!teacherRepository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("This teacher with id %s does not exists.", uuid));

        TeacherModel teacherToModify = modelMapper.map(request, TeacherModel.class);
        teacherToModify.setId(uuid);
        TeacherModel modifiedTeacher = teacherRepository.save(teacherToModify);

        return modelMapper.map(modifiedTeacher, TeacherResponseDTO.class);
    }
    

}
