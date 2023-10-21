package ednax.dio.santander.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ednax.dio.santander.restapi.dtos.request.TeacherAuthDTO;
import ednax.dio.santander.restapi.dtos.request.UserAuthDTO;
import ednax.dio.santander.restapi.services.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();


    @Test
    public void userLogin() throws Exception {
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mockito.mock(Authentication.class));

        Mockito.when(tokenService.generateUserToken(Mockito.any())).thenReturn("");


        UserAuthDTO userRequest = new UserAuthDTO("login");
        String jsonRequest = objectWriter.writeValueAsString(userRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void teacherLogin() throws Exception {
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mockito.mock(Authentication.class));

        Mockito.when(tokenService.generateUserToken(Mockito.any())).thenReturn("");


        TeacherAuthDTO teacherRequest = new TeacherAuthDTO("login", "password");
        String jsonRequest = objectWriter.writeValueAsString(teacherRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/teacher/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

}
