package com.cy.controller;

import com.cy.entity.User;
import com.cy.service.UserService;
import com.cy.utils.KeyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        String type = user.getType();
        User user1 = us.findByNameAndPassword(loginName, password,type   );
        if (user1 != null) {
            return user1.getUsername();
        } else {
            return "fail";
        }
    }

    @RequestMapping("showId")
    public Map showId(HttpServletRequest request) {
        Map map = new HashMap();
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("username");
        map.put("username",name);
        return map;
    }

    @RequestMapping("setId")
    public Map setId(HttpServletRequest request){
        Map map = new HashMap();
        map.put("rs","y");
        String name = request.getParameter("username");
        HttpSession session = request.getSession();
        session.setAttribute("username",name);
        return map;
    }


}
