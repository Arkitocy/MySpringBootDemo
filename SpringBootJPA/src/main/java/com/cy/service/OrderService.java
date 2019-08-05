package com.cy.service;

import com.cy.entity.OrderMain;
import com.cy.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {
    @Resource
    OrderRepository or;

    public OrderMain add(OrderMain order) {
        return or.save(order);  //调用repository的save方法，传入一个类
    }

}
