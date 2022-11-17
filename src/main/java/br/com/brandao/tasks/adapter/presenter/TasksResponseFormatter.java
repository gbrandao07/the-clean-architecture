package br.com.brandao.tasks.adapter.presenter;

import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import br.com.brandao.tasks.usecase.presenter.TaskPresenter;
import br.com.brandao.tasks.usecase.presenter.TasksPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class TasksResponseFormatter implements TasksPresenter {

    @Override
    public List<TaskResponseModel> prepareSuccessView(List<TaskResponseModel> response) {
        return response;
    }

    @Override
    public List<TaskResponseModel> prepareFailView(String error) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }
}