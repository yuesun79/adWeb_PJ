package com.fudan.se.community.vm;

import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import lombok.Data;

@Data
public class GroupInfo {
    Task task;
    VGroup group;
    User leader;
}
