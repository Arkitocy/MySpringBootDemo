package com.cy.service;

import com.cy.entity.Homework;
import com.cy.entity.HomeworkRankDTO;
import com.cy.entity.HomeworkUserDTO;
import com.cy.repository.DTODao;
import com.cy.repository.HomeworkRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;

import java.sql.Time;
import java.sql.Timestamp;
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
    public Iterable<Homework> findByTypeAndFinishTime(String type, Date date, Pageable pageable){
        return hr.findByTypeAndFinishTime(type,date,pageable);
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
        for (int i = 0; i < listmap.size(); i++) {
            HomeworkUserDTO hud = new HomeworkUserDTO();
            String id = (String) listmap.get(i).get("id");
            String username = (String) listmap.get(i).get("username");
            String hid = (String) listmap.get(i).get("hid");
            String homeworkid = (String) listmap.get(i).get("homeworkid");
            try {
                if (listmap.get(i).get("complete_time") != null) {
                    Date completeTime = new Date(sdf.parse(listmap.get(i).get("complete_time").toString()).getTime());
                    hud.setCompleteTime(sdf.format(completeTime));
                } else {

                    hud.setCompleteTime("");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            hud.setId(id);
            hud.setUsername(username);
            hud.setHid(hid);
            hud.setHomeworkid(homeworkid);
            if (hud.getCompleteTime() == "") {
                hud.setStatus("未完成");
            } else {
                hud.setStatus("完成");
            }
            ah.add(hud);
        }
        return ah;
    }


    public ArrayList<HomeworkRankDTO> getRank(String type1) {
        ArrayList<HomeworkRankDTO> hr = new ArrayList<>();
        List<Map<String, Object>> listmap = dd.queryRankDTOListMap(type1);
        for (int i = 0; i < listmap.size(); i++) {
            HomeworkRankDTO hrd = new HomeworkRankDTO();
            String username = (String) listmap.get(i).get("username");
            String ac = listmap.get(i).get("ac").toString();
            String type = (String) listmap.get(i).get("type");
            hrd.setUsername(username);
            hrd.setAc(ac);
            hrd.setType(type);
            hr.add(hrd);
        }
        return hr;
    }

    public int savedetails(String uid, String hid,String homeworkid) {
        int rs = 0;
        List<Homework> list = hr.findAllById(hid);
        if (new Date(System.currentTimeMillis()).getTime() > list.get(0).getFinishTime().getTime()) {
            rs = -1;
        } else {
//            dd.queryuploadDTOListMap(uid, hid, homeworkid,new Timestamp(new java.util.Date().getTime()));
//            rs = 1;
            if (dd.queryfindexit(uid,hid).size() > 0) {
                dd.queryuploadDTOListMap2(uid, hid, homeworkid,new Timestamp(new java.util.Date().getTime()));
                rs = 1;
            } else {
                dd.queryuploadDTOListMap(uid, hid, homeworkid,new Timestamp(new java.util.Date().getTime()));
                rs = 1;
            }
        }
        return rs;
    }


}
