package br.com.brandao.tasks.usecase.input.impl;

import br.com.brandao.tasks.entity.factory.TaskFactory;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.input.TaskGetAllInputBoundary;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import br.com.brandao.tasks.usecase.presenter.TasksPresenter;

import java.util.List;

public class TaskRecoverAllInteractor implements TaskGetAllInputBoundary {

    private final TaskDsGateway taskDsGateway;
    private final TasksPresenter tasksPresenter;

    public TaskRecoverAllInteractor(TaskDsGateway taskDsGateway, TasksPresenter tasksPresenter) {
        this.taskDsGateway = taskDsGateway;
        this.tasksPresenter = tasksPresenter;
    }

    @Override
    public List<TaskResponseModel> execute() {

        List<TaskResponseModel> allTasks = taskDsGateway.getAll();

        return tasksPresenter.prepareSuccessView(allTasks);
    }
}
