package br.com.brandao.tasks.usecase.input;

import br.com.brandao.tasks.usecase.model.TaskResponseModel;

import java.util.List;

public interface TaskGetAllInputBoundary {

    List<TaskResponseModel> execute();
}
