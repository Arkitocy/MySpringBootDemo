package com.cy.controller;

import com.cy.entity.Product;
import com.cy.entity.User;
import com.cy.service.ProductService;
import com.cy.utils.KeyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping("showByName")
    public List<Product> findByName(HttpServletRequest request){
        return ps.findByName(request.getParameter("productname"));
    }

    @RequestMapping("findById")
    public Optional<Product> findById(HttpServletRequest request){
        System.out.println(request.getParameter("id"));
        return ps.findById(request.getParameter("id"));
    }

    @RequestMapping("update")
    public Object update(@RequestBody Product product) {
        if (ps.save(product) != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("deleteById")
    public void deleteById(HttpServletRequest request){
        ps.deleteById(request.getParameter("id"));
    }

    @RequestMapping("save")
    private Object save(@RequestBody Product product){
        product.setId(KeyUtils.genUniqueKey());
        if (ps.save(product) != null) {
            return "success";
        } else {
            return "fail";
        }
    }
}