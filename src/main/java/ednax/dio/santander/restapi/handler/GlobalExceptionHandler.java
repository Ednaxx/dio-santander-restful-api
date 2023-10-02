package ednax.dio.santander.restapi.handler;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ednax.dio.santander.restapi.dtos.response.RestExceptionResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestExceptionResponseDTO> handleException(RestException error) {
        RestExceptionResponseDTO response = new RestExceptionResponseDTO(
            new Date(),
            error.getStatusCode().value(),
            error.getStatusCode().getReasonPhrase(),
            error.getMessage()
        );
        return ResponseEntity.status(error.getStatusCode()).body(response);
    }
}
