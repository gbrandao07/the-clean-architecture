package br.com.brandao.tasks.adapter.presenter;

import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import br.com.brandao.tasks.usecase.presenter.TaskPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaskResponseFormatter implements TaskPresenter {

    @Override
    public TaskResponseModel prepareSuccessView(TaskResponseModel response) {
        return response;
    }

    @Override
    public TaskResponseModel prepareFailView(String error) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }
}