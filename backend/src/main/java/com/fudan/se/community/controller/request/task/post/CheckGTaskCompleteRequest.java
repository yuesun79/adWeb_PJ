package com.fudan.se.community.controller.request.task.post;

import lombok.Data;

@Data
public class CheckGTaskCompleteRequest {
    private Integer userId;
    private Integer taskId;
    private Integer groupId;
}
