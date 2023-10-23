package ednax.dio.santander.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ednax.dio.santander.restapi.dtos.request.UserRequestDTO;
import ednax.dio.santander.restapi.dtos.response.UserResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void getAll() throws Exception {
        List<UserResponseDTO> users = List.of(new UserResponseDTO());

        Mockito.when(userService.findAll()).thenReturn(users);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

    @Test
    public void getBtId() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(userService.findById(id.toString())).thenReturn(new UserResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/users/%s", id))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        var userResponse = new UserResponseDTO();
        userResponse.setId(UUID.randomUUID());

        Mockito.when(userService.create(Mockito.any(UserRequestDTO.class)))
                .thenReturn(userResponse);

        UserRequestDTO userRequest = new UserRequestDTO(
                "login",
                "firstName",
                "surname",
                "male",
                new Date(),
                BigDecimal.ONE,
                1);
        String jsonRequest = objectWriter.writeValueAsString(userRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

    @Test
    public void delete() throws Exception {
        var id = UUID.randomUUID();
        Mockito.doNothing().when(userService).delete(id.toString());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(String.format("/users/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void update() throws Exception {
        var id = UUID.randomUUID();

        UserRequestDTO userRequest = new UserRequestDTO(
                "login",
                "firstName",
                "surname",
                "male",
                new Date(),
                BigDecimal.ONE,
                1);
        String jsonRequest = objectWriter.writeValueAsString(userRequest);

        Mockito.when(userService.update(id.toString(), userRequest))
                .thenReturn(new UserResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("/users/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    public void getUsersWorkoutPrograms() throws Exception {
        var id = UUID.randomUUID();

        Mockito.when(userService.findUsersWorkoutPrograms(id.toString()))
                .thenReturn(List.of(new WorkoutProgramResponseDTO()));

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/users/%s/workout-programs", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }


}
