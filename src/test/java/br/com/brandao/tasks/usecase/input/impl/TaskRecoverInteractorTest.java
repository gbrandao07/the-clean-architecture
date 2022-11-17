package br.com.brandao.tasks.usecase.input.impl;

import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import br.com.brandao.tasks.usecase.presenter.TasksPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TaskRecoverInteractorTest {

    @Mock
    private TaskDsGateway taskDsGateway;

    @Mock
    private TasksPresenter tasksPresenter;

    @InjectMocks
    private TaskRecoverAllInteractor tasksRecoverAllInteractor;

    @Test
    public void givenTasks_whenGetAll_shouldReturnAll() {

        List<TaskResponseModel> existingsTasks = new ArrayList<>();
        existingsTasks.add(new TaskResponseModel("task 1", LocalDate.parse("2050-01-01"), LocalDate.now()));
        existingsTasks.add(new TaskResponseModel("task 2", LocalDate.parse("2050-01-01"), LocalDate.now()));

        given(taskDsGateway.getAll())
                .willReturn(existingsTasks);

        given(tasksPresenter.prepareSuccessView(existingsTasks))
                .willReturn(existingsTasks);

        List<TaskResponseModel> allTasks = tasksRecoverAllInteractor.execute();

        assertEquals(2, allTasks.size());
    }
}
