package br.com.brandao.tasks.adapter.entrypoint;

import br.com.brandao.tasks.CleanArchitectureApplication;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.model.TaskRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CleanArchitectureApplication.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskRegisterControllerTest {

    @Autowired
    private TaskDsGateway taskDsGateway;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_a_task() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .content("{\"name\": \"some task\",\"startDate\": \"2050-01-01\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate").exists());

        assertThat("Should exist task", this.taskDsGateway.existsByName("some task"));

    }

    @Test
    void should_not_create_a_task_that_already_exists() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .content("{\"name\": \"some other task\",\"startDate\": \"2050-01-01\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate").exists());

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .content("{\"name\": \"some other task\",\"startDate\": \"2050-01-01\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().is5xxServerError());


    }

    @Test
    void should_not_create_a_task_with_startDate_less_than_currentDate() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .content("{\"name\": \"a task\",\"startDate\": \"2000-01-01\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

    }
}