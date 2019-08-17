package com.cy.service;

import com.cy.entity.Product;
import com.cy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    @Resource
    ProductRepository pr;

    public Iterable<Product> findall(Pageable pageable) {
        return pr.findAll(pageable);
    }

    public List<Product> findByName(String name) {
        return pr.findByName(name);
    }
    public Optional<Product> findById(String id){
        return pr.findById(id);
    }

    public Product save(Product product){
        return pr.save(product);
    }
    public void deleteById(String id){
        pr.deleteById(id);
    }
}
