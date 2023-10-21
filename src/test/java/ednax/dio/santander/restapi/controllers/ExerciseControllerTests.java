package ednax.dio.santander.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ednax.dio.santander.restapi.dtos.request.ExerciseRequestDTO;
import ednax.dio.santander.restapi.dtos.response.ExerciseResponseDTO;
import ednax.dio.santander.restapi.services.ExerciseService;
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
@WebMvcTest(ExerciseController.class)
@ContextConfiguration(classes = ExerciseController.class)
public class ExerciseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseService exerciseService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Test
    public void getAll() throws Exception {
        List<ExerciseResponseDTO> exercises = List.of(new ExerciseResponseDTO());

        Mockito.when(exerciseService.findAll()).thenReturn(exercises);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/exercises")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

    @Test
    public void getBtId() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(exerciseService.findById(id.toString())).thenReturn(new ExerciseResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/exercises/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        Mockito.when(exerciseService.create(Mockito.any(ExerciseRequestDTO.class)))
                .thenReturn(new ExerciseResponseDTO());

        ExerciseRequestDTO exerciseRequest = new ExerciseRequestDTO(
                "name",
                "duration",
                Long.toString(1L),
                "observation"
        );
        String jsonRequest = objectWriter.writeValueAsString(exerciseRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/exercises")
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
        Mockito.doNothing().when(exerciseService).delete(id.toString());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(String.format("/exercises/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void update() throws Exception {
        var id = UUID.randomUUID();

        ExerciseRequestDTO exerciseRequest = new ExerciseRequestDTO(
                "name",
                "duration",
                Long.toString(1L),
                "observation"
        );
        String jsonRequest = objectWriter.writeValueAsString(exerciseRequest);

        Mockito.when(exerciseService.update(id.toString(), exerciseRequest))
                .thenReturn(new ExerciseResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("/exercises/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

}
