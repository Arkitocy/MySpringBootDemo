package com.cy.controller;

import com.cy.entity.Homework;
import com.cy.entity.HomeworkRankDTO;
import com.cy.entity.HomeworkUserDTO;
import com.cy.repository.DTODao;
import com.cy.service.HomeworkService;
import com.cy.utils.KeyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("homework")
public class HomeworkController {
    @Resource
    HomeworkService hs;

    @Resource
    DTODao dd;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("showByType/{type}")
    public List<Homework> showByType(@PathVariable("type") String type) {

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

    @RequestMapping("findAllById/{id}")
    public List<Homework> findAllById(@PathVariable("id") String id) {
        return hs.findAllById(id);

    }

    @RequestMapping("update")
    public Homework update(@RequestBody Homework homework) {
        return hs.update(homework);
    }

    @RequestMapping("deleteById/{id}")
    public Map deleteById(@PathVariable("id") String id) {
        Map map=new HashMap();
        hs.deleteById(id);
        if (hs.findAllById(id).size() == 0) {
            map.put("rs","success");
        }else {
            map.put("rs","fail");
        }
        return map;
    }

    @RequestMapping("showdetails/{id}")
    public ArrayList<HomeworkUserDTO> showdetails(@PathVariable("id") String hid) {
        return hs.gethomeworkdetail(hs.findAllById(hid).get(0));
    }


    @RequestMapping("upload")
    public Map  upload()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map map=new HashMap();

//        dd.queryuploadDTOListMap(hid,uid,new java.util.Date());

        map.put("rs","success");
        return map;
    }

    @RequestMapping("rank/{type}")
    public ArrayList<HomeworkRankDTO> rank(@PathVariable("type") String type) {
        return hs.getRank(type);
    }
}
