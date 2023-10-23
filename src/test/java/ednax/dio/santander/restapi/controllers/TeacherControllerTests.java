package ednax.dio.santander.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ednax.dio.santander.restapi.dtos.request.TeacherRequestDTO;
import ednax.dio.santander.restapi.dtos.response.TeacherResponseDTO;
import ednax.dio.santander.restapi.dtos.response.WorkoutProgramResponseDTO;
import ednax.dio.santander.restapi.services.implementations.TeacherServiceImpl;
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
@WebMvcTest(TeacherController.class)
@ContextConfiguration(classes = TeacherController.class)
public class TeacherControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherServiceImpl teacherService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();


    @Test
    public void getAll() throws Exception {
        List<TeacherResponseDTO> teachers = List.of(new TeacherResponseDTO());

        Mockito.when(teacherService.findAll()).thenReturn(teachers);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/teachers")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void getById() throws Exception {
        var id = UUID.randomUUID();
        Mockito.when(teacherService.findById(id.toString())).thenReturn(new TeacherResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/teachers/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void create() throws Exception {
        var teacherResponse = new TeacherResponseDTO();
        teacherResponse.setId(UUID.randomUUID());

        Mockito.when(teacherService.create(Mockito.any(TeacherRequestDTO.class)))
                .thenReturn(teacherResponse);

        TeacherRequestDTO teacherRequest = new TeacherRequestDTO(
                "firstName",
                "surname",
                "login",
                "password"
        );
        String jsonRequest = objectWriter.writeValueAsString(teacherRequest);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/teachers")
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
        Mockito.doNothing().when(teacherService).delete(id.toString());

        RequestBuilder request = MockMvcRequestBuilders
                .delete(String.format("/teachers/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void update() throws Exception {
        var id = UUID.randomUUID();

        TeacherRequestDTO teacherRequest = new TeacherRequestDTO(
                "firstName",
                "surname",
                "login",
                "password");
        String jsonRequest = objectWriter.writeValueAsString(teacherRequest);

        Mockito.when(teacherService.update(id.toString(), teacherRequest))
                .thenReturn(new TeacherResponseDTO());

        RequestBuilder request = MockMvcRequestBuilders
                .put(String.format("/teachers/%s", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonRequest);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void getTeachersWorkoutPrograms() throws Exception {
        var id = UUID.randomUUID();

        Mockito.when(teacherService.findTeachersWorkoutPrograms(id.toString()))
                .thenReturn(List.of(new WorkoutProgramResponseDTO()));

        RequestBuilder request = MockMvcRequestBuilders
                .get(String.format("/teachers/%s/workout-programs", id.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }


}
