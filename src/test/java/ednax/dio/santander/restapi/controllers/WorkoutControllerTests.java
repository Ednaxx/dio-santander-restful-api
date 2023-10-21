package ednax.dio.santander.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ednax.dio.santander.restapi.dtos.request.WorkoutRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.services.implementations.WorkoutServiceImpl;
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

import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(WorkoutController.class)
@ContextConfiguration(classes = WorkoutController.class)
public class WorkoutControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutServiceImpl workoutService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void getAll() throws Exception {
        List<WorkoutResponseDTO> workouts = List.of(new WorkoutResponseDTO());

        Mockito.when(workoutService.findAll()).thenReturn(workouts);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/workouts")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

    @Test
    public void getBtId() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(workoutService.findById(id.toString())).thenReturn(new WorkoutResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/workouts/%s", id))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        Mockito.when(workoutService.create(Mockito.any(WorkoutRequestDTO.class)))
                .thenReturn(new WorkoutResponseDTO());

        WorkoutRequestDTO workoutRequest = new WorkoutRequestDTO(
                "name",
                UUID.randomUUID().toString()
        );
        String jsonRequest = objectWriter.writeValueAsString(workoutRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/workouts")
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
        Mockito.doNothing().when(workoutService).delete(id.toString());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(String.format("/workouts/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void update() throws Exception {
        var id = UUID.randomUUID();

        WorkoutRequestDTO workoutRequest = new WorkoutRequestDTO(
                "name",
                UUID.randomUUID().toString()
        );
        String jsonRequest = objectWriter.writeValueAsString(workoutRequest);

        Mockito.when(workoutService.update(id.toString(), workoutRequest))
                .thenReturn(new WorkoutResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("/workouts/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    public void getWorkoutsExercises() throws Exception {
        var id = UUID.randomUUID();

        Mockito.when(workoutService.findWorkoutsExercises(id.toString()))
                .thenReturn(List.of(new ExerciseResponseDTO()));

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/workouts/%s/exercises", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

}
