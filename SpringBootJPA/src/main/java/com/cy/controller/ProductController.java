package com.cy.controller;

import com.cy.entity.Product;
import com.cy.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("product")
public class ProductController {
    @Resource
    ProductService ps;

    @RequestMapping("showall")
    public Iterable<Product> findall(){
        Iterable<Product> ip = ps.findall();
        return ip;
    }
}
