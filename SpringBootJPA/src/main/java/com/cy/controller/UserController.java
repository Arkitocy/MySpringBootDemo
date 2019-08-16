package com.cy.controller;

import com.cy.entity.User;
import com.cy.service.UserService;
import com.cy.utils.JwtUtil;
import com.cy.utils.KeyUtils;
import com.mysql.cj.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;


@RestController

public class UserController {
    //使用SpringIOC控制反转，让spring容器创建对象
    //userservice上添加@Service注解
    @Resource
    UserService us;

    @RequestMapping("register")
    public Object save(@RequestBody User user) {
        user.setId(KeyUtils.genUniqueKey());
        if (us.save(user) != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("checkName")
    @ResponseBody
    public Map checkName(HttpServletRequest request) {
        String username = request.getParameter("username");
        List<User> user = us.findByName(username);
        boolean result = false;
        if (user.size() > 0) {
            result = true;
        }
        Map map = new HashMap();
        map.put("result", result);
        return map;
    }

    @RequestMapping("login")
    public Object login(@RequestBody User user) {
        String loginName = user.getUsername();
        String password = user.getPassword();
        User user1 = us.findByNameAndPassword(loginName, password);
        if (user1 != null) {
            return user1.getUsername();
        } else {
            return "";
        }
    }

    @RequestMapping("edit")
    public Object edie(@RequestBody User user) {
        if (us.save(user) != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("findUserID/{username}")
    public Map findUserID(@PathVariable("username") String username){
        Map map=new HashMap();
        List<User> user = us.findByName(username);
        map.put("id",user.get(0).getId());
        map.put("head",user.get(0).getHead());
        return map;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map upload(@RequestParam MultipartFile myFile, HttpSession session) throws IOException {
        String originalFilename = myFile.getOriginalFilename();
        int pos = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(pos);
        String realPath = "D:/tmp";
        String uuid = UUID.randomUUID().toString();
        String fullPath = realPath + File.separator + uuid + suffix;
        String homeworkid=File.separator + uuid + suffix;
        InputStream in = null;
        try {
            in = myFile.getInputStream();
            OutputStream out = new FileOutputStream(new File(fullPath));
            int len = 0;
            byte[] buf = new byte[100 * 1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map map= new HashMap();
        map.put("result",homeworkid);
        return map;
    }

}
