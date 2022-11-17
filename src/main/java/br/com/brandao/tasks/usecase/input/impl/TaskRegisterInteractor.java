package br.com.brandao.tasks.usecase.input.impl;

import br.com.brandao.tasks.entity.Task;
import br.com.brandao.tasks.entity.factory.TaskFactory;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.input.TaskCreateInputBoundary;
import br.com.brandao.tasks.usecase.model.TaskDsRequestModel;
import br.com.brandao.tasks.usecase.model.TaskRequestModel;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import br.com.brandao.tasks.usecase.presenter.TaskPresenter;

import java.time.LocalDate;

public class TaskRegisterInteractor implements TaskCreateInputBoundary {

    private final TaskDsGateway taskDsGateway;
    private final TaskPresenter taskPresenter;
    private final TaskFactory taskFactory;

    public TaskRegisterInteractor(TaskDsGateway taskDsGateway, TaskPresenter taskPresenter, TaskFactory taskFactory) {
        this.taskDsGateway = taskDsGateway;
        this.taskPresenter = taskPresenter;
        this.taskFactory = taskFactory;
    }

    @Override
    public TaskResponseModel execute(TaskRequestModel requestModel) {

        if (taskDsGateway.existsByName(requestModel.getName())) {
            return taskPresenter.prepareFailView("Task already exists.");
        }

        Task task = taskFactory.create(requestModel.getName(), requestModel.getStartDate());
        if (!task.isValidStartDate()) {
            return taskPresenter.prepareFailView("Task start date should be less than actual date.");
        }

        LocalDate creationDate = LocalDate.now();
        TaskDsRequestModel taskDsModel = new TaskDsRequestModel(task.getName(), task.getStartDate(), creationDate);

        taskDsGateway.save(taskDsModel);

        TaskResponseModel taskResponseModel = new TaskResponseModel(task.getName(), task.getStartDate(), creationDate);
        return taskPresenter.prepareSuccessView(taskResponseModel);
    }
}
