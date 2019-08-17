package com.cy.repository;

import com.cy.entity.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework,String> {
    List<Homework> findByTypeAndFinishTime(String type, Date date);
    List<Homework> findAllById(String id);
    Page<Homework> findByTypeAndFinishTime(String type, Date date, Pageable pageable);
}
