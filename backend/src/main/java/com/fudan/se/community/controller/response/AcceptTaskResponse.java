package com.fudan.se.community.controller.response;

import lombok.Data;

@Data
public class AcceptTaskResponse {
    private String message;
    private String process; // 任务已接受过：结束/未开始（maybe人没凑齐）/进行中/已提交
}
