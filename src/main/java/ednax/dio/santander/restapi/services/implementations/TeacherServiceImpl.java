package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.services.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public TeacherResponseDTO create(TeacherRequestDTO request) {
        TeacherModel teacherToSave = modelMapper.map(request, TeacherModel.class);

        if (repository.findByLogin(teacherToSave.getLogin()) != null) throw new IllegalArgumentException("This teacher already exists.");

        TeacherModel savedTeacher = repository.save(teacherToSave);

        TeacherResponseDTO response = modelMapper.map(savedTeacher, TeacherResponseDTO.class);
        
        // Manually inserting Workout programs into response body
        if(!(savedTeacher.getWorkoutPrograms() == null)) response.setWorkoutPrograms(new ArrayList<>(savedTeacher.getWorkoutPrograms())); 
        else response.setWorkoutPrograms(new ArrayList<>());

        return response;
    }

    @Override
    public void delete(String id) {
        var uuid = UUID.fromString(id);

        if(!repository.findById(uuid).isPresent()) throw new IllegalArgumentException(String.format("The teacher with id %s does not exists.", uuid));

        repository.deleteById(uuid);
    }

    @Override
    public List<TeacherResponseDTO> findAll() {
        List<TeacherModel> teacherModels = repository.findAll();
        List<TeacherResponseDTO> response = new ArrayList<>();

        teacherModels.forEach(teacher -> {
            TeacherResponseDTO responseTeacher = modelMapper.map(teacher, TeacherResponseDTO.class);

            // Manually inserting Workout programs into response body
            if (!(teacher.getWorkoutPrograms() == null)) responseTeacher.setWorkoutPrograms(new ArrayList<>(teacher.getWorkoutPrograms()));
            else responseTeacher.setWorkoutPrograms(new ArrayList<>());

            response.add(responseTeacher);
        });

        return response;
    }

    @Override
    public TeacherResponseDTO findById(String id) {
        var uuid = UUID.fromString(id);
        TeacherModel teacher = repository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("The teacher with the specified Id does not exists."));

        TeacherResponseDTO response = modelMapper.map(teacher, TeacherResponseDTO.class);

        // Manually inserting Workout programs into response body
        if(!(teacher.getWorkoutPrograms() == null)) response.setWorkoutPrograms(new ArrayList<>(teacher.getWorkoutPrograms())); 
        else response.setWorkoutPrograms(new ArrayList<>());

        return response;
    }

    @Override
    public TeacherResponseDTO update(String id, TeacherRequestDTO request) {
        var uuid = UUID.fromString(id);

        TeacherModel oldTeacher = repository.findById(uuid).orElseThrow(
            () -> new IllegalArgumentException(String.format("The teacher with id %s does not exists.", uuid)
        ));

        TeacherModel teacherToModify = modelMapper.map(request, TeacherModel.class);
        teacherToModify.setId(uuid);
        teacherToModify.setWorkoutPrograms(oldTeacher.getWorkoutPrograms());
        TeacherModel modifiedTeacher = repository.save(teacherToModify);

        TeacherResponseDTO response = modelMapper.map(modifiedTeacher, TeacherResponseDTO.class);
        
        // Manually inserting Workout programs into response body
        if(!(modifiedTeacher.getWorkoutPrograms() == null)) response.setWorkoutPrograms(new ArrayList<>(modifiedTeacher.getWorkoutPrograms())); 
        else response.setWorkoutPrograms(new ArrayList<>());

        return response;
    }
    
    @Override
    public List<WorkoutProgramResponseDTO> findTeachersWorkoutPrograms(String id) {
        var uuid = UUID.fromString(id);

        TeacherModel teacher = repository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("The teacher with the specified Id does not exists."));

        List<WorkoutProgramModel> workoutPrograms = teacher.getWorkoutPrograms();
        List<WorkoutProgramResponseDTO> response = new ArrayList<>();

        workoutPrograms.forEach(
            workout -> response.add(modelMapper.map(workout, WorkoutProgramResponseDTO.class))
            );
            
        return response;
    }


}
