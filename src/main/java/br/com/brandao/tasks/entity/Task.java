package br.com.brandao.tasks.entity;

import java.time.LocalDate;

public interface Task {

    boolean isValidStartDate();

    String getName();

    LocalDate getStartDate();

}


/*

Business rules
- The task start date should not be less than actual date

Application rules
- The system receives the tasks name and start date,
validates if the task doesn't exist, and saves the new task along with the creation time

 */