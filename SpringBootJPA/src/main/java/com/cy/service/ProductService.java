package com.cy.service;

import com.cy.entity.Product;
import com.cy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ProductService {
    @Resource
    ProductRepository pr;

    public Iterable<Product> findall() {
        return pr.findAll();
    }

}
