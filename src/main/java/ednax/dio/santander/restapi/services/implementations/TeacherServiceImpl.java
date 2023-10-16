package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.TeacherModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.TeacherRepository;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.services.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public TeacherResponseDTO create(TeacherRequestDTO request) {
        TeacherModel teacherToSave = modelMapper.map(request, TeacherModel.class);

        if (repository.findByLogin(teacherToSave.getLogin()) != null) throw new RestException(HttpStatus.CONFLICT, "A Teacher with this login already exists.");
        if (userRepository.findByLogin(teacherToSave.getLogin()) != null) throw new RestException(HttpStatus.CONFLICT, "A User with this login already exists.");

        // Encrypting password
        String encryptedPassword = new BCryptPasswordEncoder().encode(teacherToSave.getPassword());
        teacherToSave.setPassword(encryptedPassword);

        TeacherModel savedTeacher = repository.save(teacherToSave);

        TeacherResponseDTO response = modelMapper.map(savedTeacher, TeacherResponseDTO.class);
        
        // To return Workout Programs as empty Array
        response.setWorkoutPrograms(new ArrayList<>());

        return response;
    }

    @Override
    public void delete(String id) {
        var uuid = validateTeacherId(id);

        if(!repository.findById(uuid).isPresent()) throw new RestException(HttpStatus.NOT_FOUND, String.format("The Teacher with id %s does not exists.", id));

        repository.deleteById(uuid);
    }

    @Override
    public List<TeacherResponseDTO> findAll() {
        List<TeacherModel> teacherModels = repository.findAll();
        List<TeacherResponseDTO> response = new ArrayList<>();

        teacherModels.forEach(teacher -> {
            TeacherResponseDTO responseTeacher = modelMapper.map(teacher, TeacherResponseDTO.class);

            setWorkoutProgramsIntoResponseBody(teacher, responseTeacher);

            response.add(responseTeacher);
        });

        return response;
    }

    @Override
    public TeacherResponseDTO findById(String id) {
        var uuid = validateTeacherId(id);
        TeacherModel teacher = repository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The Teacher with id %s does not exists.", id)));

        TeacherResponseDTO response = modelMapper.map(teacher, TeacherResponseDTO.class);

        setWorkoutProgramsIntoResponseBody(teacher, response);

        return response;
    }

    @Override
    public TeacherResponseDTO update(String id, TeacherRequestDTO request) {
        var uuid = validateTeacherId(id);

        TeacherModel oldTeacher = repository.findById(uuid).orElseThrow(
            () -> new RestException(HttpStatus.NOT_FOUND, String.format("The Teacher with id %s does not exists.", id)
        ));

        TeacherModel teacherToModify = modelMapper.map(request, TeacherModel.class);
        teacherToModify.setId(uuid);
        teacherToModify.setWorkoutPrograms(oldTeacher.getWorkoutPrograms());
        TeacherModel modifiedTeacher = repository.save(teacherToModify);

        TeacherResponseDTO response = modelMapper.map(modifiedTeacher, TeacherResponseDTO.class);
        setWorkoutProgramsIntoResponseBody(modifiedTeacher, response);

        return response;
    }
    
    @Override
    public List<WorkoutProgramResponseDTO> findTeachersWorkoutPrograms(String id) {
        var uuid = validateTeacherId(id);

        TeacherModel teacher = repository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The Teacher with id %s does not exists.", id)));

        List<WorkoutProgramModel> workoutPrograms = teacher.getWorkoutPrograms();
        List<WorkoutProgramResponseDTO> response = new ArrayList<>();

        workoutPrograms.forEach(
            workout -> response.add(modelMapper.map(workout, WorkoutProgramResponseDTO.class))
            );
            
        return response;
    }
    
    private void setWorkoutProgramsIntoResponseBody(TeacherModel teacher, TeacherResponseDTO response) {
        if(!(teacher.getWorkoutPrograms() == null)) response.setWorkoutPrograms(new ArrayList<>(teacher.getWorkoutPrograms())); 
        else response.setWorkoutPrograms(new ArrayList<>());
    }

    static UUID validateTeacherId(String id) {
        try {
            return UUID.fromString(id);
        }
        catch (Exception e) {
            throw new RestException(HttpStatus.BAD_REQUEST, String.format("%s is not a valid UUID", id));
        }
    }

}
