package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class AssignGroupInfoRequest {
    private Integer groupId;
    private String name;
    private Integer groupLeader;
}
