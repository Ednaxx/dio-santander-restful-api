package ednax.dio.santander.restapi.handler;

import ednax.dio.santander.restapi.dtos.response.RestExceptionResponseDTO;
import ednax.dio.santander.restapi.exceptions.RestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTests {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void shouldHandleException() {
        RestException exception = new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "error");
        ResponseEntity<RestExceptionResponseDTO> response = globalExceptionHandler.handleException(exception);

        Assertions.assertInstanceOf(ResponseEntity.class, response);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getBody().getError());
        Assertions.assertEquals("error", response.getBody().getMessage());
        Assertions.assertInstanceOf(Date.class, response.getBody().getTimestamp());
    }


}
