package com.fudan.se.community.pojo.vm;

import lombok.Data;
import lombok.ToString;

import java.sql.Blob;

@Data
@ToString
public class GroupTask extends Task {

    private Integer groupId;
    private String groupName;
    private Integer groupLeader;
    private Integer groupProcess;
    private Blob file;
}
