package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class SubmitPTaskRequest {
    private long taskId;
    private byte[] file;
}
