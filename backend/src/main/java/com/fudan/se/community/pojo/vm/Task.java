package com.fudan.se.community.pojo.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudan.se.community.pojo.user.User;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Task {
    // task + publisher_user
    private Integer id;
    private String name;
    private String description;
    private Integer ev;
    private Integer optional;
    private Integer teamSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp ddl;
    private Integer validity;

    private Integer publisherId;
    private String publisherName;
    private String publisherEmail;
    private String publisherPhone;

    private Integer isFree; //是否为个人发布的自由任务，1代表是，0代表不是。
    public Task(){

    }
    public Task(User user, com.fudan.se.community.pojo.task.Task task) {
     this.ddl=task.getDdl();
     this.description=task.getDescription();
     this.ev=task.getEv();
     this.isFree=task.getIsFree();
     this.validity=task.getValidity();
     this.name=task.getName();
     this.teamSize=task.getTeamSize();
     this.id=task.getId();
     this.optional=task.getOptional();

     this.publisherEmail=user.getEmail();
     this.publisherId=user.getId();
     this.publisherName=user.getUsername();
     this.publisherPhone=user.getPhoneNum();


    }
}
