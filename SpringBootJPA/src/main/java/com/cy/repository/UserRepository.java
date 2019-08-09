package com.cy.repository;

import com.cy.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,String> {
     List<User> findByUsername(String username);

     User findByUsernameAndPassword(String username,String password);
}
