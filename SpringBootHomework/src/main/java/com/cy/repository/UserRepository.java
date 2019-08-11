package com.cy.repository;

import com.cy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
     List<User> findByUsername(String username);

     User findByUsernameAndPasswordAndType(String username, String password,String type);
}
