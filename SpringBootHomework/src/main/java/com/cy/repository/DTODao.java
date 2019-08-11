package com.cy.repository;

import com.cy.entity.Homework;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class DTODao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryHomeworkUserDTOListMap(Homework homework) {
        String sql = "SELECT u.id,u.username,uh.h_id,uh.complete_time from `user` u LEFT JOIN user_homework uh on (u.id=uh.u_id  and uh.h_id= ? )where  u.type='student'";
        Object[] args = {homework.getId()};
        int[] argTypes = {Types.VARCHAR};

        return this.jdbcTemplate.queryForList(sql, args, argTypes);
    }
}
