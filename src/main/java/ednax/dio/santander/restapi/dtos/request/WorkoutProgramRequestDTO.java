package ednax.dio.santander.restapi.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutProgramRequestDTO {
    String name;
    @NotNull
    String teacherId;
    @NotBlank String objective;
}
