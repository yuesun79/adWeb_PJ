package com.fudan.se.community.controller.request.task.post;

import lombok.Data;

@Data
public class CheckPTaskCompleteRequest {
     private Integer nowId;
     private Integer userId;
     private Integer taskId;

}
