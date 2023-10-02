package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.models.WorkoutProgramModel;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.services.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        UserModel userToSave = modelMapper.map(request, UserModel.class);

        if (repository.findByLogin(userToSave.getLogin()) != null) throw new RestException(HttpStatus.CONFLICT, "A User with this login already exists.");

        UserModel savedUser = repository.save(userToSave);

        UserResponseDTO response = modelMapper.map(savedUser, UserResponseDTO.class);
        
        // To return Workout Programs as empty Array
        response.setWorkoutPrograms(new ArrayList<>());

        return response;
    }

    @Override
    public void delete(String id) {
        var uuid = UUID.fromString(id);

        if(!repository.findById(uuid).isPresent()) throw new RestException(HttpStatus.BAD_REQUEST, String.format("The User with id %s does not exists.", id));

        repository.deleteById(uuid);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<UserModel> userModels = repository.findAll();
        List<UserResponseDTO> response = new ArrayList<>();

        userModels.forEach(user -> {
            UserResponseDTO responseUser = modelMapper.map(user, UserResponseDTO.class);
            setWorkoutProgramsIntoResponseBody(user, responseUser);

            response.add(responseUser);
        });

        return response;
    }

    @Override
    public UserResponseDTO findById(String id) {
        var uuid = UUID.fromString(id);

        UserModel user = repository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The User with id %s does not exists.", id)));
        
        UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);

        setWorkoutProgramsIntoResponseBody(user, response);

        return response;
    }

    @Override
    public UserResponseDTO update(String id, UserRequestDTO request) {
        var uuid = UUID.fromString(id);

        UserModel oldUser = repository.findById(uuid).orElseThrow(
            () -> new RestException(HttpStatus.BAD_REQUEST, String.format("The User with id %s does not exists.", id)
        ));

        UserModel userToModify = modelMapper.map(request, UserModel.class);
        userToModify.setId(uuid);
        userToModify.setWorkoutPrograms(oldUser.getWorkoutPrograms());
        UserModel modifiedUser = repository.save(userToModify);
        
        UserResponseDTO response = modelMapper.map(modifiedUser, UserResponseDTO.class);
        
        setWorkoutProgramsIntoResponseBody(modifiedUser, response);

        return response;
    }

    @Override
    public List<WorkoutProgramResponseDTO> findUsersWorkoutPrograms(String id) {
        var uuid = UUID.fromString(id);

        UserModel user = repository.findById(uuid).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, String.format("The User with id %s does not exists.", id)));

        List<WorkoutProgramModel> workoutPrograms = user.getWorkoutPrograms();
        List<WorkoutProgramResponseDTO> response = new ArrayList<>();

        workoutPrograms.forEach(
            workout -> response.add(modelMapper.map(workout, WorkoutProgramResponseDTO.class))
            );
            
        return response;
    }

    private void setWorkoutProgramsIntoResponseBody(UserModel user, UserResponseDTO response) {
        if(!(user.getWorkoutPrograms() == null)) response.setWorkoutPrograms(new ArrayList<>(user.getWorkoutPrograms())); 
        else response.setWorkoutPrograms(new ArrayList<>());
    }
    
}
