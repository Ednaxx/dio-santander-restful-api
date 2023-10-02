package ednax.dio.santander.restapi.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestException extends RuntimeException {
    private final HttpStatus statusCode;
    
    public RestException(HttpStatus httpStatus, String errorMessage){
        super(errorMessage);
        this.statusCode = httpStatus;
    }
}
