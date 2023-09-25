package ednax.dio.santander.restapi.dtos.request;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank String login;
    @NotBlank String firstName;
    @NotBlank String surname;
    @NotNull String sex;
    @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East") Date birthday;
    @NotNull @Min(0) @Max(999) @Digits(integer = 3, fraction = 2) BigDecimal weight;
    @NotNull @Min(0) @Max(999) Integer height;
}
