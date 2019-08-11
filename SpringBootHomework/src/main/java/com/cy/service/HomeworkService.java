package com.cy.service;

import com.cy.entity.Homework;
import com.cy.repository.HomeworkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class HomeworkService {
    @Resource
    HomeworkRepository hr;

    public List<Homework> findByTypeAndFinishTime(String type, Date date) {
        return hr.findByTypeAndFinishTime(type, date);
    }

    public Homework save(Homework homework) {
        return hr.save(homework);
    }

    public List<Homework> findAllById(String id) {
        return hr.findAllById(id);
    }

    public Homework update(Homework homework) {
        return hr.save(homework);
    }

    public void deleteById(String id) {
        hr.deleteById(id);
    }
}
