package br.com.brandao.tasks.adapter.config;

import br.com.brandao.tasks.adapter.dataprovider.JpaTaskDataProvider;
import br.com.brandao.tasks.adapter.dataprovider.jpa.repository.JpaTaskRepository;
import br.com.brandao.tasks.adapter.presenter.TaskResponseFormatter;
import br.com.brandao.tasks.entity.factory.TaskFactory;
import br.com.brandao.tasks.entity.factory.impl.CommonTaskFactory;
import br.com.brandao.tasks.usecase.gateway.TaskDsGateway;
import br.com.brandao.tasks.usecase.input.TaskCreateInputBoundary;
import br.com.brandao.tasks.usecase.input.impl.TaskRegisterInteractor;
import br.com.brandao.tasks.usecase.presenter.TaskPresenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIConfig {

    @Bean
    public TaskDsGateway taskDsGateway(JpaTaskRepository jpaTaskRepository) {
        return new JpaTaskDataProvider(jpaTaskRepository);
    }

    @Bean
    public TaskPresenter taskPresenter() {
        return new TaskResponseFormatter();
    }

    @Bean
    public TaskFactory taskFactory() {
        return new CommonTaskFactory();
    }

    @Bean
    public TaskCreateInputBoundary taskCreateInputBoundary(JpaTaskRepository jpaTaskRepository) {
        return new TaskRegisterInteractor(taskDsGateway(jpaTaskRepository), taskPresenter(), taskFactory());
    }
}

