package com.fudan.se.community.controller.request.task;

import lombok.Data;

import java.util.Map;

@Data
public class AssignEVRequest {
    private Integer userId;
    private Integer groupId;
    // 试试吧不知道对不对
    private Map<Integer, Double> score;
}
