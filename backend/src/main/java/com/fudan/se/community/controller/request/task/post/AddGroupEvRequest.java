package com.fudan.se.community.controller.request.task.post;
import lombok.Data;

@Data
public class AddGroupEvRequest {
    private Integer userId;
    private Integer ev;   //该队员的经验值

}
