package br.com.brandao.tasks.usecase.presenter;

import br.com.brandao.tasks.usecase.model.TaskResponseModel;

import java.util.List;

public interface TasksPresenter {

    List<TaskResponseModel> prepareSuccessView(List<TaskResponseModel> task);

    List<TaskResponseModel> prepareFailView(String error);
}
