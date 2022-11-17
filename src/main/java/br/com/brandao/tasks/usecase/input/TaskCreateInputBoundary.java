package br.com.brandao.tasks.usecase.input;

import br.com.brandao.tasks.usecase.model.TaskRequestModel;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;

public interface TaskCreateInputBoundary {

    TaskResponseModel execute(TaskRequestModel requestModel);
}
