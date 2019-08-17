package com.cy.repository;


import com.cy.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,String> {
    List<Product> findByName(String name);
    Optional<Product> findById(String id);

    Iterable<Product> findAll(Sort sort);
    Page<Product> findAll(Pageable pageable);
}
