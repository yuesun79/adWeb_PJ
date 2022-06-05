package com.fudan.se.community.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    String type;
    String id;
    String message;

    public Message(String id) {
        this.type = "Message";
        this.id = id;
    }

    public Message(String id, String message) {
        this.type = "Message";
        this.id = id;
        this.message = message;
    }
}
