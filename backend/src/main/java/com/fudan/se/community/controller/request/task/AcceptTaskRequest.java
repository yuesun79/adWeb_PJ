package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class AcceptTaskRequest {
    private Integer userId;
    private Integer taskId;
}
