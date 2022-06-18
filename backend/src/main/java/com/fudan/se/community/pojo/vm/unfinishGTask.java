package com.fudan.se.community.pojo.vm;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class unfinishGTask {
    private Integer id;
    private String name;
    private String description;
    private Integer ev;
    private Integer optional;
    private Integer teamSize;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp ddl;
    private Integer validity;
    private Integer isFree;
    private String path;
    private Integer checked;
    private String file;
    private String groupName;
    private Integer groupLeader;
    private Integer process;
    private Integer groupId;

    private Integer publisher_id;  //发布者id
    private String publisher_name;
    private String publiser_phone;
    private String publisher_email;

     private Integer uploaderId;
    private  String uploaderUsername; //提交者的名字
    private String uploaderPhone ;//提交者名字
    private String uploaderEmail;// 提交者邮箱

    public unfinishGTask(){

    }

    public unfinishGTask(com.fudan.se.community.pojo.task.Task task, VGroup group,User pubUser,User user) {
        this.ddl = task.getDdl();
        this.description = task.getDescription();
        this.ev = task.getEv();
        this.isFree = task.getIsFree();
        this.validity = task.getValidity();
        this.name = task.getName();
        this.teamSize = task.getTeamSize();
        this.id = task.getId();
        this.optional = task.getOptional();
        this.path=group.getPath();
        this.checked = group.getChecked();
        this.file = group.getFile();

        this.groupId=group.getId();
        this.groupName = group.getName();
        this.groupLeader=group.getGroupLeader();
        this.process = group.getProcess();

        this.publisher_email = pubUser.getEmail();
        this.publisher_id = pubUser.getId();
        this.publiser_phone = pubUser.getPhoneNum();
        this.publisher_name = pubUser.getUsername();
        if (group.getFile()!=null) {
            this.uploaderId=user.getId();
            this.uploaderUsername = user.getUsername();
            this.uploaderEmail = user.getEmail();
            this.uploaderPhone = user.getPhoneNum();
        }
    }
}
