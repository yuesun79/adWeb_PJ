package com.fudan.se.community.pojo.message;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room")
@NoArgsConstructor
public class ChatMessage extends Message {
    Integer roomId;
    Integer tid;
    String tUsername;
    boolean status = false;

    public ChatMessage(Integer sid, String message, Integer roomId) {
        super(sid, message);
        this.type = "ChatMessage";
        this.roomId = roomId;
    }

    public ChatMessage(Integer sid, Integer tid, String message, Integer roomId) {
        super(sid, message);
        this.type = "ChatMessage";

        this.tid = tid;
        this.roomId = roomId;
    }
}
