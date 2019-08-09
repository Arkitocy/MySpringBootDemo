package com.cy.service;


import com.cy.entity.User;
import com.cy.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserService {
    @Resource
    UserRepository ur;

    public User save(User user) {
        return ur.save(user);
    }

    public List<User> findByName(String name){
        return ur.findByUsername(name);
    }

    public User findByNameAndPassword(String name,String password){
        return ur.findByUsernameAndPassword(name,password);
    }

}
