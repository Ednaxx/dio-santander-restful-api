package ednax.dio.santander.restapi.dtos.response;

import java.util.List;
import java.util.UUID;

import ednax.dio.santander.restapi.models.WorkoutProgramModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {
    UUID id;
    String firstName;
    String surname;
    String login;
    List<WorkoutProgramModel> workoutPrograms;
}
