package com.fudan.se.community.controller.request.task.post;

import lombok.Data;

@Data
public class CheckGTaskCompleteRequest {
    private long userId;
    private long taskId;
    private long groupId;
}