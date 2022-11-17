package br.com.brandao.tasks.usecase.gateway;

import br.com.brandao.tasks.usecase.model.TaskDsRequestModel;

public interface TaskDsGateway {

    boolean existsByName(String name);

    void save(TaskDsRequestModel requestModel);

    // TODO implementar restante do crud
}
