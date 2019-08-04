package com.cy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyspringbootController {
    @RequestMapping("test")
    public String test() {
        return " hello";
    }
}
