package br.com.brandao.tasks.adapter.dataprovider.jpa.repository;

import br.com.brandao.tasks.adapter.dataprovider.jpa.entity.JpaTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends JpaRepository<JpaTask, Long> {
}