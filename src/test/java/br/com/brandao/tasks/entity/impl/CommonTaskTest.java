package br.com.brandao.tasks.entity.impl;

import br.com.brandao.tasks.entity.Task;
import br.com.brandao.tasks.entity.factory.TaskFactory;
import br.com.brandao.tasks.entity.factory.impl.CommonTaskFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonTaskTest {

    @Test
    public void givenTask_whenStartDateLessThanActualDate_shouldFail() {

        TaskFactory factory = new CommonTaskFactory();
        Task task = factory.create("Task in the past", LocalDate.parse("1900-01-01"));
        assertFalse(task.isValidStartDate());
    }

    @Test
    public void givenTask_whenStartDateGreaterThanActualDate_shouldSuccess() {

        TaskFactory factory = new CommonTaskFactory();
        Task task = factory.create("Task in the future far far away", LocalDate.parse("2050-01-01"));
        assertTrue(task.isValidStartDate());
    }
}
