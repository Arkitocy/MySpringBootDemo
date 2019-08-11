package com.cy.controller;

import com.cy.entity.Homework;
import com.cy.service.HomeworkService;
import com.cy.utils.KeyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("homework")
public class HomeworkController {
    @Resource
    HomeworkService hs;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("showByType")
    public List<Homework> showByType(HttpServletRequest request) {
        String type = request.getParameter("type");
        java.util.Date d1 = new java.util.Date();
        if (d1.getHours() <= 11 && "竞赛".equals(type)) {
            Date datesql = new Date(d1.getTime());
            return hs.findByTypeAndFinishTime(type, datesql);
        } else if (d1.getHours() >= 12 && "结对".equals(type)) {
            Date datesql2 = new Date(d1.getTime());
            return hs.findByTypeAndFinishTime(type, datesql2);
        } else {
            return null;
        }
    }

    @RequestMapping("showByTypeAndFinishTime")
    public List<Homework> showByTypeAndFinishTime(HttpServletRequest request) {
        String type = request.getParameter("type");
        java.util.Date d1 = null;
        try {
            d1 = sdf.parse(request.getParameter("finishTime"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = new Date(d1.getTime());
        return hs.findByTypeAndFinishTime(type, d2);
    }

    @RequestMapping("save")
    public Homework save(@RequestBody Homework homework) {
        homework.setId(KeyUtils.genUniqueKey());
        homework.setUpdateTime(new Date(new java.util.Date().getTime()));
        return hs.save(homework);
    }

    @RequestMapping("findAllById")
    public List<Homework> findAllById(HttpServletRequest request) {
        String id = request.getParameter("id");
        return hs.findAllById(id);

    }

    @RequestMapping("update")
    public Homework update(@RequestBody Homework homework) {
        return hs.update(homework);
    }

    @RequestMapping("deleteById")
    public Object deleteById(HttpServletRequest request) {
        String id = request.getParameter("id");
        hs.deleteById(id);
        if (hs.findAllById(id).size()==0) {
            System.out.println("success");
            return "success";
        }
        return "fail";
    }

}
