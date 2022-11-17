package br.com.brandao.tasks.usecase.input.impl;

import br.com.brandao.tasks.entity.factory.TaskFactory;
import br.com.brandao.tasks.entity.impl.CommonTask;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.model.TaskDsRequestModel;
import br.com.brandao.tasks.usecase.model.TaskRequestModel;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import br.com.brandao.tasks.usecase.presenter.TaskPresenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class TaskRegisterInteractorTest {

    @Mock
    private TaskDsGateway taskDsGateway;

    @Mock
    private TaskPresenter taskPresenter;

    @Mock
    private TaskFactory taskFactory;

    @InjectMocks
    private TaskRegisterInteractor taskRegisterInteractor;

    @Test
    public void givenTask_whenTaskAlreadyExists_shouldFail() {

        LocalDate startDate = LocalDate.parse("2050-01-01");
        String taskName = "Task that already was created";

        given(taskDsGateway.existsByName(taskName))
                .willReturn(true);

        taskRegisterInteractor.execute(new TaskRequestModel(taskName, startDate));

        then(taskPresenter).should()
                .prepareFailView("Task already exists.");

    }

    @Test
    public void givenTask_whenStartDateLessThanActualDate_shouldFail() {

        LocalDate startDate = LocalDate.parse("1900-01-01");
        String taskName = "Task in the past";

        given(taskDsGateway.existsByName(taskName))
                .willReturn(false);

        given(taskFactory.create(taskName, startDate))
                .willReturn(new CommonTask(taskName, startDate));

        taskRegisterInteractor.execute(new TaskRequestModel(taskName, startDate));

        then(taskPresenter).should()
                .prepareFailView("Task start date should be less than actual date.");

    }

    @Test
    public void givenTask_whenStartDateGreaterThanActualDate_shouldSuccess() {

        LocalDate startDate = LocalDate.parse("2100-01-01");
        LocalDate creationDate = LocalDate.now();
        String taskName = "Task in the future far far away";

        given(taskDsGateway.existsByName(taskName))
                .willReturn(false);

        given(taskFactory.create(taskName, startDate))
                .willReturn(new CommonTask(taskName, startDate));

        taskRegisterInteractor.execute(new TaskRequestModel(taskName, startDate));

        then(taskDsGateway).should()
                .save(new TaskDsRequestModel(taskName, startDate, creationDate));

        then(taskPresenter).should()
                .prepareSuccessView(new TaskResponseModel(taskName, startDate, creationDate));

    }
}
