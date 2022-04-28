package com.fudan.se.community.controller.request.task;

import lombok.Data;

@Data
public class SubmitGTaskRequest {
    private long groupId;
    private byte[] file;
}
