package com.madaless.work_cards_api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madaless.work_cards_api.dtos.EmployeeDto;
import com.madaless.work_cards_api.dtos.ProjectDto;
import com.madaless.work_cards_api.mapper.EmployeeMapper;
import com.madaless.work_cards_api.mapper.ProjectMapper;
import com.madaless.work_cards_api.mapper.SimpleEmployeeMapper;
import com.madaless.work_cards_api.services.EmployeeService;
import com.madaless.work_cards_api.services.InscriptionService;
import com.madaless.work_cards_api.services.ProjectService;
import com.madaless.work_cards_api.services.WorkCardService;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

  @Autowired
  EmployeeController employeeController;
  @Autowired
  ProjectController projectController;
  @MockBean
  private EmployeeService employeeService;
  @MockBean
  private ProjectService projectService;
  @MockBean
  private WorkCardService workCardService;
  @MockBean
  private ProjectMapper projectMapper;
  @MockBean
  private EmployeeMapper employeeMapper;
  @MockBean
  private SimpleEmployeeMapper simpleEmployeeMapper;
  @MockBean
  private InscriptionService inscriptionService;
  @Autowired
  private MockMvc mockMvc;


  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void addEmployee_goodData() throws Exception {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("Bob");
    employeeDto.setLastName("Bobowski");
    employeeDto.setPhoneNumber("123456789");

    MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
    String employee = "{\"firstName\": \"bob\", \"lastName\" : \"Bobowski\", \"phoneNumber\" : \"465456545\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/employees")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)
        .content(asJsonString(employeeDto)).characterEncoding("utf-8")).andDo(print())
        .andExpect(status().isCreated()).andReturn();
  }

  @Test
  void addEmployee_badData() throws Exception {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("B");
    employeeDto.setLastName("ki");
    employeeDto.setPhoneNumber("123656456789");

    MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
    mockMvc.perform(MockMvcRequestBuilders.post("/employees")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)
        .content(asJsonString(employeeDto)).characterEncoding("utf-8"))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void deleteEmployee() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 1))
        .andExpect(status().isOk());
  }

  @Test
  void getAllEmployees() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/employees")
        .accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getEmployee() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/employees/{id}", 2)
        .accept(MediaType.ALL).characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void generateRapport() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/employees/{id}/rapport", 2)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk()).andExpect(status().isOk());

  }

  @Test
  void generateRapports() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/employees/rapports")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void saveProject_goodData() throws Exception {
    ProjectDto projectDto = ProjectDto.builder().name("project name").description("description")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/projects")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)
        .content(asJsonString(projectDto)).characterEncoding("utf-8"))
        .andExpect(status().isCreated());
  }

  @Test
  void saveProject_badData() throws Exception {
    ProjectDto projectDto = ProjectDto.builder().name("").description("description").build();

    mockMvc.perform(MockMvcRequestBuilders.post("/projects")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)
        .content(asJsonString(projectDto)).characterEncoding("utf-8"))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void getProject() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/projects/{id}", 2)
        .accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void deleteProject() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{id}", 1))
        .andExpect(status().isOk());
  }


  @Test
  void getStatistics() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
        .get("/projects/statistics")
        .accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk()).andExpect(header().exists("Number of projects"));
  }

  // did not have a more time for more or better tests

}