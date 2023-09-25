package ednax.dio.santander.restapi.dtos.response;

import java.util.List;

import ednax.dio.santander.restapi.models.ExerciseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutResponseDTO {
    Long id;
    String name;
    List<ExerciseModel> exercises;
}
