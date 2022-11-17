package br.com.brandao.tasks.usecase.presenter;

import br.com.brandao.tasks.usecase.model.TaskResponseModel;

public interface TaskPresenter {

    TaskResponseModel prepareSuccessView(TaskResponseModel task);

    TaskResponseModel prepareFailView(String error);
}
