package com.cy.entity;

import java.sql.Date;

public class HomeworkUserDTO {
    private String id;
    private String username;
    private String hid;
    private String completeTime;
    private String status;
    private String homeworkid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }



    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHomeworkid() {
        return homeworkid;
    }

    public void setHomeworkid(String homeworkid) {
        this.homeworkid = homeworkid;
    }
}
