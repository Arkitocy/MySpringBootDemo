package com.cy.controller;

import com.cy.entity.Product;
import com.cy.entity.User;
import com.cy.service.ProductService;
import com.cy.utils.KeyUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {
    @Resource
    ProductService ps;

    @RequestMapping("showall/{page}")
    public Iterable<Product> findall(@PathVariable("page") String page) {
        Pageable pageable = new PageRequest(Integer.parseInt(page),3);
        Iterable<Product> ip = ps.findall(pageable);
        return ip;
    }

    @RequestMapping("showByName")
    public List<Product> findByName(HttpServletRequest request) {
        return ps.findByName(request.getParameter("productname"));
    }

    @RequestMapping("findById")
    public Optional<Product> findById(HttpServletRequest request) {
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
    public void deleteById(HttpServletRequest request) {
        ps.deleteById(request.getParameter("id"));
    }

    @RequestMapping("save")
    private Object save(@RequestBody Product product) {
        product.setId(KeyUtils.genUniqueKey());
        if (ps.save(product) != null) {
            return "success";
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
