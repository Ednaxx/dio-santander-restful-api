package ednax.dio.santander.restapi.dtos.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestExceptionResponseDTO {
    
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;

}
