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
public class unfinishTask {
    private Integer id;
    private String name;
    private String description;
    private Integer ev;
    private Integer optional;
    private Integer teamSize;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp ddl;
    private Integer validity;
    private Integer isFree;
    private Integer checked;
   private Integer userId;  //接受任务，也是提交任务的id
   private String file;
   private String path;
   private Integer publisher_id;  //发布者id
   private String publisher_name;
   private String publiser_phone;
   private String publisher_email;

    private Integer uploaderId;
    private  String uploaderUsername; //提交者的名字
    private String uploaderPhone ;//提交者名字
    private String uploaderEmail;// 提交者邮箱
    private Integer acceptId;

    private Integer groupId;
    private String groupName;
    private String groupFile;
    private String groupPath;



   public unfinishTask(com.fudan.se.community.pojo.task.Task task, Accept accept,User pubUser,User user) {
       this.ddl = task.getDdl();
       this.description = task.getDescription();
       this.ev = task.getEv();
       this.isFree = task.getIsFree();
       this.validity = task.getValidity();
       this.name = task.getName();
       this.teamSize = task.getTeamSize();
       this.id = task.getId();
       this.optional = task.getOptional();
       this.checked = accept.getChecked();
       this.file = accept.getFile();
       this.userId = accept.getUserId();
       this.path=accept.getPath();
       this.acceptId=accept.getId();

       this.publisher_email = pubUser.getEmail();
       this.publisher_id = pubUser.getId();
       this.publiser_phone = pubUser.getPhoneNum();
       this.publisher_name = pubUser.getUsername();
          if (accept.getFile()!=null) {
              this.uploaderId=user.getId();
              this.uploaderUsername = user.getUsername();
              this.uploaderEmail = user.getEmail();
              this.uploaderPhone = user.getPhoneNum();
          }

   }

    }
