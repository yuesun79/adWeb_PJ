package com.fudan.se.community.controller.request.task;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class AcceptTaskRequest {
    Integer userId;
    Integer groupId;
}
