package br.com.brandao.tasks.usecase.gateway;

import br.com.brandao.tasks.usecase.model.TaskDsRequestModel;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;

import java.util.List;

public interface TaskDsGateway {

    boolean existsByName(String name);

    void save(TaskDsRequestModel requestModel);

    List<TaskResponseModel> getAll();
}
