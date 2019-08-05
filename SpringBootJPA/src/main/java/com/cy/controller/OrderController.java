package com.cy.controller;


import com.cy.entity.OrderMain;
import com.cy.service.OrderService;
import com.cy.utils.KeyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {
    @Resource //注入service
    OrderService os;

    @RequestMapping("add")
    public OrderMain save(HttpServletRequest request){
        OrderMain or = new OrderMain();
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        or.setAddress(address);
        or.setName(name);
        or.setId(KeyUtils.genUniqueKey());
        return os.add(or);

    }


}
