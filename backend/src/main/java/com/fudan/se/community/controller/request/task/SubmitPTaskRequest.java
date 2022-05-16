package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class SubmitPTaskRequest {
    private Integer userId;
    private Integer taskId;
    private byte[] file;
}
