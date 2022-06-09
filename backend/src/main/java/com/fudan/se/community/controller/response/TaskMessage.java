package com.fudan.se.community.controller.response;

import lombok.Data;

@Data
public class TaskMessage extends Message {
    public TaskMessage(String id, String message, Integer roomId) {
        super(id, message, roomId);
        this.type = "TaskMessage";
    }
}
