package ednax.dio.santander.restapi.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ednax.dio.santander.restapi.dtos.request.ExerciseRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.models.ExerciseModel;
import ednax.dio.santander.restapi.repositories.ExerciseRepository;
import ednax.dio.santander.restapi.services.ExerciseService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    
    private final ExerciseRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public ExerciseResponseDTO create(ExerciseRequestDTO request) {
        ExerciseModel exerciseToSave = modelMapper.map(request, ExerciseModel.class);
        ExerciseModel savedExercise = repository.save(exerciseToSave);

        return modelMapper.map(savedExercise, ExerciseResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        var longId = Long.parseLong(id);

        if(!repository.findById(longId).isPresent()) throw new IllegalArgumentException(String.format("The Exercise with id %s does not exists.", longId));
        
        repository.deleteById(longId);
    }

    @Override
    public List<ExerciseResponseDTO> findAll() {
        List<ExerciseModel> exerciseModels = repository.findAll();
        List<ExerciseResponseDTO> response = new ArrayList<>();

        exerciseModels.forEach(exercise -> {
            response.add(modelMapper.map(exercise, ExerciseResponseDTO.class));
        });

        return response;
    }

    @Override
    public ExerciseResponseDTO findById(String id) {
        var longId = Long.parseLong(id);

        ExerciseModel exercise = repository.findById(longId).orElseThrow(() -> new IllegalArgumentException("The exercise with the specified Id does not exists."));

        return modelMapper.map(exercise, ExerciseResponseDTO.class);
    }

    @Override
    public ExerciseResponseDTO update(String id, ExerciseRequestDTO request) {
        var longId = Long.parseLong(id);

        if(!repository.findById(longId).isPresent()) throw new IllegalArgumentException(String.format("The Exercise with id %s does not exists.", longId));

        ExerciseModel exerciseToModify = modelMapper.map(request, ExerciseModel.class);
        ExerciseModel modifiedExercise = repository.save(exerciseToModify);
        ExerciseResponseDTO respose = modelMapper.map(modifiedExercise, ExerciseResponseDTO.class);

        return respose;
    }


}
