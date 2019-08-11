package com.cy.service;

import com.cy.entity.Homework;
import com.cy.entity.HomeworkUserDTO;
import com.cy.repository.DTODao;
import com.cy.repository.HomeworkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HomeworkService {
    @Resource
    HomeworkRepository hr;
    @Resource
    DTODao dd;

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


    public ArrayList<HomeworkUserDTO> gethomeworkdetail(Homework homework) {
        ArrayList<HomeworkUserDTO> ah = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> listmap = dd.queryHomeworkUserDTOListMap(homework);
        System.out.println(listmap);
        for (int i = 0; i < listmap.size(); i++) {
            HomeworkUserDTO hud = new HomeworkUserDTO();
            String id = (String) listmap.get(i).get("id");
            String username = (String) listmap.get(i).get("username");
            String hid = (String) listmap.get(i).get("hid");
            try {
                if (listmap.get(i).get("complete_time")!=null){
                    Date completeTime = new Date(sdf.parse( listmap.get(i).get("complete_time").toString()).getTime());
                    hud.setCompleteTime(completeTime);
                }else {

                    Date completeTime=null;
                    hud.setCompleteTime(completeTime);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            hud.setId(id);
            hud.setUsername(username);
            hud.setHid(hid);
            if (hud.getCompleteTime() == null) {
                hud.setStatus("未完成");
            } else {
                hud.setStatus("完成");
            }
            ah.add(hud);

        }
        return ah;
    }
}
