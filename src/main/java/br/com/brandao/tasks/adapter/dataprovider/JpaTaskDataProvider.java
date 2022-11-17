package br.com.brandao.tasks.adapter.dataprovider;

import br.com.brandao.tasks.adapter.dataprovider.jpa.entity.JpaTask;
import br.com.brandao.tasks.adapter.dataprovider.jpa.repository.JpaTaskRepository;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.model.TaskDsRequestModel;
import br.com.brandao.tasks.usecase.model.TaskResponseModel;
import lombok.Data;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<TaskResponseModel> getAll() {
        List<TaskResponseModel> tasks = new ArrayList<>();
        List<JpaTask> jpaTasks = jpaTaskRepository.findAll();
        jpaTasks.stream().forEach(jpaTask ->
            tasks.add(new TaskResponseModel(jpaTask.getName(), jpaTask.getStartDate(), jpaTask.getCreationDate()))
        );
        return tasks;
    }
}
