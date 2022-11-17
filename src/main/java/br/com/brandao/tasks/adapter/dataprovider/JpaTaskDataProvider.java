package br.com.brandao.tasks.adapter.dataprovider;

import br.com.brandao.tasks.adapter.dataprovider.jpa.entity.JpaTask;
import br.com.brandao.tasks.adapter.dataprovider.jpa.repository.JpaTaskRepository;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.model.TaskDsRequestModel;
import lombok.Data;
import org.springframework.data.domain.Example;

@Data
public class JpaTaskDataProvider implements TaskDsGateway {

    private final JpaTaskRepository jpaTaskRepository;

    @Override
    public boolean existsByName(String name) {
        JpaTask jpaTask = new JpaTask();
        jpaTask.setName(name);
        Example<JpaTask> taskQueryExample = Example.of(jpaTask);
        return jpaTaskRepository.exists(taskQueryExample);
    }

    @Override
    public void save(TaskDsRequestModel requestModel) {
        JpaTask jpaTask = new JpaTask();
        jpaTask.setName(requestModel.getName());
        jpaTask.setStartDate(requestModel.getStartDate());
        jpaTask.setCreationDate(requestModel.getCreationDate());
        jpaTaskRepository.save(jpaTask);
    }
}
