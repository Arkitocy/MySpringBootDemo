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
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Value("${fileUpLoadPath}")
    String filePath;

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

    @RequestMapping("showByTypeAndFinishTime/{page}")
    public Iterable<Homework> showByTypeAndFinishTime(@PathVariable("page") String page, HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), 10);
        String type = request.getParameter("type");
        java.util.Date d1 = null;
        try {
            d1 = sdf.parse(request.getParameter("finishTime"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = new Date(d1.getTime());
        return hs.findByTypeAndFinishTime(type, d2, pageable);
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
        Map map = new HashMap();
        hs.deleteById(id);
        if (hs.findAllById(id).size() == 0) {
            map.put("rs", "success");
        } else {
            map.put("rs", "fail");
        }
        return map;
    }

    @RequestMapping("showdetails/{id}")
    public ArrayList<HomeworkUserDTO> showdetails(@PathVariable("id") String hid) {
        return hs.gethomeworkdetail(hs.findAllById(hid).get(0));
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map upload(@RequestParam MultipartFile myFile, HttpSession session) throws IOException {
        String originalFilename = myFile.getOriginalFilename();
        int pos = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(pos);

        String uuid = UUID.randomUUID().toString();
        String fullPath = filePath + File.separator + uuid + suffix;
        String homeworkid = File.separator + uuid + suffix;
        InputStream in = null;
        try {
            in = myFile.getInputStream();
            OutputStream out = new FileOutputStream(new File(fullPath));
            int len = 0;
            byte[] buf = new byte[3 * 1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("result", homeworkid);
        return map;
    }



    @RequestMapping(value = "/download/{homeworkid}", method = RequestMethod.GET)
    public void downLoad(@PathVariable("homeworkid") String homeworkid, HttpServletResponse response) {
        List<Map<String, Object>> list = dd.queryfindhomework(homeworkid);
        for (int i=0;i<list.size();i++){
            String fileName = (String)list.get(i).get("homeworkid");
            String fullPath = filePath + File.separator + fileName;
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名;
            //response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1"));
            try {
                File downloadFile = new File(fullPath);
                FileInputStream inputStream = new FileInputStream(downloadFile);
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping("saveDetails/{uid}/{hid}/{result}")
    public Map saveDetails(@PathVariable("uid") String uid, @PathVariable("hid") String hid, @PathVariable("result") String homeworkid) {
        Map map = new HashMap();
        int rs = hs.savedetails(uid, hid, homeworkid);
        if (rs > 0) {
            map.put("rs", "success");
        } else if (rs == -1) {
            map.put("rs", "outtime");
        } else {
            map.put("rs", "fail");
        }
        return map;
    }

    @RequestMapping("rank/{type}")
    public ArrayList<HomeworkRankDTO> rank(@PathVariable("type") String type) {
        return hs.getRank(type);
    }
}
