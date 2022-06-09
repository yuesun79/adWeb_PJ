package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class ListGroupTasksRequest {
    private Integer userId;
    private Integer taskId;
}
