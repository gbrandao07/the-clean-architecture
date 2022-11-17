package br.com.brandao.tasks.adapter.entrypoint;

import br.com.brandao.tasks.CleanArchitectureApplication;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CleanArchitectureApplication.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskRecoverAllControllerTest {

    @Autowired
    private TaskDsGateway taskDsGateway;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_all_tasks() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .content("{\"name\": \"some task1\",\"startDate\": \"2050-01-01\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .content("{\"name\": \"some task2\",\"startDate\": \"2050-01-01\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists());

        assertThat("Should return tasks", taskDsGateway.getAll().size() == 2);

    }
}