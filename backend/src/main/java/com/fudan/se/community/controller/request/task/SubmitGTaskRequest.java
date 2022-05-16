package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class SubmitGTaskRequest {
    private Integer taskId;
    private Integer groupId;
    private byte[] file;
}
