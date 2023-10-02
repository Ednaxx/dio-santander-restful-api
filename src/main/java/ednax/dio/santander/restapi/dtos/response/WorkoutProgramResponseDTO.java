package ednax.dio.santander.restapi.dtos.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutProgramResponseDTO {
    UUID id;
    String name;
    String objective;
    String userName;
    String teacherName;
}
