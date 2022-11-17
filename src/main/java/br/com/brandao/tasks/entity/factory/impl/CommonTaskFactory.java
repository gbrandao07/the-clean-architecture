package br.com.brandao.tasks.entity.factory.impl;

import br.com.brandao.tasks.entity.Task;
import br.com.brandao.tasks.entity.factory.TaskFactory;
import br.com.brandao.tasks.entity.impl.CommonTask;

import java.time.LocalDate;

public class CommonTaskFactory implements TaskFactory {

    @Override
    public Task create(String name, LocalDate startDate) {
        return new CommonTask(name, startDate);
    }
}
