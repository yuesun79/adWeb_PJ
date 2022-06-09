package com.fudan.se.community.controller.response;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    String type;
    String id;
    String message;

    Integer roomId;

    public Message(String id, Integer roomId) {
        this.type = "Message";
        this.id = id;
        this.roomId = roomId;
    }

    public Message(String id, String message, Integer roomId) {
        this.type = "Message";
        this.id = id;
        this.message = message;
        this.roomId = roomId;
    }

}
