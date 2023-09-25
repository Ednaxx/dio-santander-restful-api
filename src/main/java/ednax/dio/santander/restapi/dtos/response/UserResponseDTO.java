package ednax.dio.santander.restapi.dtos.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import ednax.dio.santander.restapi.models.WorkoutProgramModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    UUID id;
    String login;
    String firstName;
    String surname;
    String sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    Date birthday;
    BigDecimal weight;
    Integer height;
    List<WorkoutProgramModel> workouts;
}
