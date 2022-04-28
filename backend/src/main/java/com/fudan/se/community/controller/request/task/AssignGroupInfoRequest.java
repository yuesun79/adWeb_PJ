package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class AssignGroupInfoRequest {
    private long taskId;
    private long groupId;
    private String name;
    private long groupLeader;
}
