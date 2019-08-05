package com.cy.controller;

import com.cy.entity.User;
import com.cy.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController

public class UserController {
    //使用SpringIOC控制反转，让spring容器创建对象
    @Resource
    UserService us;

    @RequestMapping("getAll")
    public ArrayList<User> getAll() {
        return us.showAll();
    }

    @RequestMapping("delete")
    public Map remove(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean result = us.delete(id);
        Map map = new HashMap();
        map.put("result", result);
        return map;
    }

    @RequestMapping("login")
    @ResponseBody
    public Object login(@RequestBody User user) {
        if (us.loginUser(user.getUsername(), user.getPassword()).size() > 0) {
            return "success";
        } else {
            return "fail";
        }
    }


    @RequestMapping("register")
    @ResponseBody
    public Object register(@RequestBody User user) {
        if (us.registerUser(user.getUsername(), user.getPassword()) != null) {
            return "success";
        } else {
            return "fail";
        }
    }


    @RequestMapping("checkName")
    @ResponseBody
    public Map checkName(HttpServletRequest request) {
        UserService us = new UserService();
        String username = request.getParameter("username");
        User user = us.findByName(username);
        boolean result = false;
        if (user != null) {
            result = true;
        }
        Map map = new HashMap();
        map.put("result", result);
        return map;


    }

}
