package com.fudan.se.community.vm;

import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import lombok.Data;
import lombok.ToString;

import java.sql.Blob;

@Data
@ToString
public class GroupTask extends Task {
//    private Task task;
//    private VGroup group;
//    private User user;
    private Integer groupId;
    private String groupName;
    private Integer groupLeader;
    private Integer groupProcess;
    private Blob file;
}
