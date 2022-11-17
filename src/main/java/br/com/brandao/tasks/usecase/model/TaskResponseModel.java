package br.com.brandao.tasks.usecase.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponseModel {

    private final String name;
    private final LocalDate startDate;
    private final LocalDate creationDate;
}
