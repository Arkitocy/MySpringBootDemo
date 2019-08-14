package com.cy.controller;

import com.cy.bean.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class DemoController {

    @RequestMapping("/demo1")
    public String to_demo1(Model m) {
        m.addAttribute("username","user1");
        m.addAttribute("user",new Account("KangKang", "康康", "e10adc3949ba59abbe56e", "超级管理员", "17777777777"));

        List<Account> list = new ArrayList<Account>();
        list.add(new Account("KangKang", "康康", "e10adc3949ba59abbe56e", "超级管理员", "17777777777"));
        list.add(new Account("Mike", "麦克", "e10adc3949ba59abbe56e", "管理员", "13444444444"));
        list.add(new Account("Jane","简","e10adc3949ba59abbe56e","运维人员","18666666666"));
        list.add(new Account("Maria", "玛利亚", "e10adc3949ba59abbe56e", "清算人员", "19999999999"));
        m.addAttribute("accountList",list);




        return "demo1";
    }


    @RequestMapping(value = { "/saveProduct" }, method = RequestMethod.POST)
    public String saveprodcut(Model model, Account account) throws ParseException {
        System.out.println(account.getName());
        //List productList = DAO.loadAllProducts();
        //model.addAttribute("productList", productList);
        return "account";
    }
}