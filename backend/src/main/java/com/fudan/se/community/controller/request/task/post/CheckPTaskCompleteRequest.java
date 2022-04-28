package com.fudan.se.community.controller.request.task.post;

import lombok.Data;

@Data
public class CheckPTaskCompleteRequest {
     private long userId;
     private long taskId;
}
