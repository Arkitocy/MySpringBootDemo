package com.cy.repository;


import com.cy.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,String> {
    List<Product> findByName(String name);
    Optional<Product> findById(String id);

}
