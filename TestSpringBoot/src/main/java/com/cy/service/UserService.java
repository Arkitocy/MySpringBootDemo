package com.cy.service;


import com.cy.comment.Connecter;
import com.cy.comment.KeyUtils;
import com.cy.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Service
public class UserService {
    Connection cn = null;

    public User registerUser(String name, String pwd) {
        User user = null;
        try {
            cn = new Connecter().getConnetcion();
            String id = KeyUtils.genUniqueKey();
            String sql1 = "insert into tb_user(id,username,password) values (?,?,?);";
            PreparedStatement ps1 = cn.prepareStatement(sql1);
            ps1.setString(1, id);
            ps1.setString(2, name);
            ps1.setString(3, pwd);
            int rs = ps1.executeUpdate();
            if (rs >= 1) {
                user = new User(id, name, pwd);
            }
            ps1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    public ArrayList<User> loginUser(String name, String pwd) {
        ArrayList<User> arrayList = new ArrayList<>();
        try {
            cn = new Connecter().getConnetcion();
            String sql = "select * from tb_user where username= ? and password=?;";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                arrayList.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("登陆失败");
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        }
    }

    public User findByName(String name) {
        User user = null;
        try {
            cn = new Connecter().getConnetcion();
            String sql = "select * from tb_user where username = ?;";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<User> showAll() {
        ArrayList<User> arrayList = new ArrayList<>();
        try {
            cn = new Connecter().getConnetcion();
            String sql = "select * from tb_user ;";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                arrayList.add(user);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public boolean delete(String id) {
        boolean f = false;
        try {
            cn = new Connecter().getConnetcion();
            String sql = "delete from tb_user where id=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            if (rs >= 1) {
                f = true;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return f;
        }
    }
}
