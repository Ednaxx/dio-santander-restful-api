package ednax.dio.santander.restapi.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponseDTO {
    Long id;
    String name;
    String duration;
    String observation;
}
