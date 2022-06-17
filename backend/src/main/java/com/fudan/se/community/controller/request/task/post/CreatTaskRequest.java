package com.fudan.se.community.controller.request.task.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

public class CreatTaskRequest {
    public Integer userId; //当前创建任务的用户ID
    //任务所需参数
    public String name; //任务名
    public String description; //任务描述
    public Integer ev;
    public Integer optional;
    public Integer team_size;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     public Timestamp ddl;
    //班级ID
    public Integer classId;
}
