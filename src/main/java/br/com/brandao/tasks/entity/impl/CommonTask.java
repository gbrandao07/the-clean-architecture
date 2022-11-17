package br.com.brandao.tasks.entity.impl;

import br.com.brandao.tasks.entity.Task;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommonTask implements Task {

    private final String name;
    private final LocalDate startDate;

    @Override
    public boolean isValidStartDate() {
        return startDate.isAfter(LocalDate.now());
    }
}
