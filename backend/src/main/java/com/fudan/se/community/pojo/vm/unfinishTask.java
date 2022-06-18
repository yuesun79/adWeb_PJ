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
   private Integer userId;
   private String file;

   public unfinishTask(com.fudan.se.community.pojo.task.Task task, Accept accept){
       this.ddl=task.getDdl();
       this.description=task.getDescription();
       this.ev=task.getEv();
       this.isFree=task.getIsFree();
       this.validity=task.getValidity();
       this.name=task.getName();
       this.teamSize=task.getTeamSize();
       this.id=task.getId();
       this.optional=task.getOptional();
       this.checked=accept.getChecked();
       this.file=accept.getFile();
       this.userId=accept.getUserId();
   }



    }
