package com.cy.repository;

import com.cy.entity.Homework;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DTODao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryHomeworkUserDTOListMap(Homework homework) {
        String sql = "SELECT u.id,u.username,uh.h_id,uh.complete_time,uh.homeworkid from `user` u LEFT JOIN user_homework uh on (u.id=uh.u_id  and uh.h_id= ? )where  u.type='student'";
        Object[] args = {homework.getId()};
        int[] argTypes = {Types.VARCHAR};

        return this.jdbcTemplate.queryForList(sql, args, argTypes);
    }


    public int queryuploadDTOListMap(String uid, String hid, String homeworkid,Date date) {
        String sql = "insert into user_homework(h_id,u_id,complete_time,homeworkid,status) values(?,?,?,?,?)";
        Object[] args = {hid, uid, date, homeworkid,"完成"};
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR};

        return this.jdbcTemplate.update(sql, args);
    }

    public int queryuploadDTOListMap2(String uid, String hid,String homeworkid, Date date) {
        String sql = "update user_homework set complete_time =? , homeworkid=? where u_id=? AND h_id=?";
        Object[] args = {date,homeworkid, uid,hid};
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR};

        return this.jdbcTemplate.update(sql, args);
    }

    public List<Map<String, Object>> queryRankDTOListMap(String type) {
        if ("全部".equals(type)) {
            String sql = "SELECT `user`.`username`,COUNT(user_homework.`status`) ac, `user`.type FROM `user` LEFT JOIN user_homework ON `user`.id=user_homework.u_id GROUP BY `user`.id HAVING `user`.type='student' ORDER BY COUNT(user_homework.`status`) DESC";
            return this.jdbcTemplate.queryForList(sql);
        } else {
            String sql2 = "SELECT `user`.`username`,count(t.h_id) ac ,`user`.type FROM `user` LEFT JOIN (SELECT `user`.id,`user`.`username`,user_homework.h_id FROM `user` LEFT JOIN user_homework on `user`.id = user_homework.u_id LEFT JOIN homework on user_homework.h_id=homework.id where homework.type=?) t on t.id = `user`.id GROUP BY `user`.id HAVING `user`.type='student' ORDER BY COUNT(t.h_id) desc;";
            Object[] args = {type};
            int[] argTypes = {Types.VARCHAR};
            return this.jdbcTemplate.queryForList(sql2, args, argTypes);
        }
    }

    public List<Map<String, Object>> queryfindhomework(String homeworkid) {
        String sql = ("select * from user_homework where homeworkid=? ");
        Object[] args = {homeworkid};
        int[] argTypes = {Types.VARCHAR};
        return this.jdbcTemplate.queryForList(sql, args,argTypes);
    }

    public List<Map<String, Object>> queryfindexit(String uid,String hid) {
        String sql = ("select * from user_homework where u_id=? AND h_id=? ");
        Object[] args = {uid,hid};
        int[] argTypes = {Types.VARCHAR,Types.VARCHAR};
        return this.jdbcTemplate.queryForList(sql, args,argTypes);
    }


}
