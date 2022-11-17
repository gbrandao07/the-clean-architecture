package br.com.brandao.tasks.adapter.dataprovider.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class JpaTask {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate creationDate;
}

