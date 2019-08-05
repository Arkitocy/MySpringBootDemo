package com.cy.repository;

import com.cy.entity.OrderMain;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderMain,String>{
//(类型，主键类型)
}
