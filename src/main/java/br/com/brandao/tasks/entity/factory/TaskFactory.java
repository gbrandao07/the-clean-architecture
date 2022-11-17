package br.com.brandao.tasks.entity.factory;

import br.com.brandao.tasks.entity.Task;

import java.time.LocalDate;

public interface TaskFactory {

    Task create(String name, LocalDate startDate);
}
