package br.com.brandao.tasks.usecase.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestModel {

    private final String name;
    private final LocalDate startDate;
}
