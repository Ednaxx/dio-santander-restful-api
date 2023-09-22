package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;
import ednax.dio.santander.restapi.models.UserModel;
import ednax.dio.santander.restapi.repositories.UserRepository;
import ednax.dio.santander.restapi.services.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        UserModel userToSave = modelMapper.map(request, UserModel.class);
        UserModel savedUser = userRepository.save(userToSave);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        var uuid = UUID.fromString(id);
        userRepository.deleteById(uuid);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<UserModel> userModels = userRepository.findAll();
        List<UserResponseDTO> response = new ArrayList<>();

        userModels.forEach(user -> {
            response.add(modelMapper.map(user, UserResponseDTO.class));
        });

        return response;
    }

    @Override
    public UserResponseDTO findById(String id) {
        var uuid = UUID.fromString(id);

        UserModel user = userRepository.findById(uuid).orElseThrow(NoSuchElementException::new);
        
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO update(String id, UserRequestDTO user) {
        var uuid = UUID.fromString(id);

        UserModel userToModify = modelMapper.map(user, UserModel.class);
        userToModify.setId(uuid);
        UserModel modifiedUser = userRepository.save(userToModify);

        return modelMapper.map(modifiedUser, UserResponseDTO.class);
    }
    
}
