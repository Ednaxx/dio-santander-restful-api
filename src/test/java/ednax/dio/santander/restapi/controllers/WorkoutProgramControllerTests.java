package ednax.dio.santander.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ednax.dio.santander.restapi.dtos.request.WorkoutProgramRequestDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutResponseDTO;
import ednax.dio.santander.restapi.services.implementations.WorkoutProgramServiceImpl;
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
@WebMvcTest(WorkoutProgramController.class)
@ContextConfiguration(classes = WorkoutProgramController.class)
public class WorkoutProgramControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutProgramServiceImpl workoutProgramService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void getAll() throws Exception {
        List<WorkoutProgramResponseDTO> workoutPrograms = List.of(new WorkoutProgramResponseDTO());

        Mockito.when(workoutProgramService.findAll()).thenReturn(workoutPrograms);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/workout-programs")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

    @Test
    public void getBtId() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(workoutProgramService.findById(id.toString())).thenReturn(new WorkoutProgramResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/workout-programs/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        Mockito.when(workoutProgramService.create(Mockito.any(WorkoutProgramRequestDTO.class)))
                .thenReturn(new WorkoutProgramResponseDTO());

        WorkoutProgramRequestDTO workoutProgramRequest = new WorkoutProgramRequestDTO(
                "name",
                UUID.randomUUID().toString(),
                "objective"
        );
        String jsonRequest = objectWriter.writeValueAsString(workoutProgramRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/workout-programs")
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
        Mockito.doNothing().when(workoutProgramService).delete(id.toString());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(String.format("/workout-programs/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void update() throws Exception {
        var id = UUID.randomUUID();

        WorkoutProgramRequestDTO workoutProgramRequest = new WorkoutProgramRequestDTO(
                "name",
                UUID.randomUUID().toString(),
                "objective"
        );
        String jsonRequest = objectWriter.writeValueAsString(workoutProgramRequest);

        Mockito.when(workoutProgramService.update(id.toString(), workoutProgramRequest))
                .thenReturn(new WorkoutProgramResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("/workout-programs/%s", id.toString()))
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

        Mockito.when(workoutProgramService.findProgramsWorkouts(id.toString()))
                .thenReturn(List.of(new WorkoutResponseDTO()));

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/workout-programs/%s/workouts", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

}
