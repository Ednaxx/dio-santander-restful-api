package ednax.dio.santander.restapi.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequestDTO {
    @NotBlank String firstName;
    @NotBlank String surname;
    @NotBlank String login;
    @NotBlank String password;
}
