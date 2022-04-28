package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class AcceptTaskRequest {
    private long userId;
    private long taskId;
}
