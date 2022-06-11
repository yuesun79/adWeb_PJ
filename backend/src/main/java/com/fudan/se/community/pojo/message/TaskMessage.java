package com.fudan.se.community.pojo.message;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room")
public class TaskMessage extends ChatMessage {
    Integer teamMem;
    Integer teamSize;

    public TaskMessage(Integer sid, String message, Integer roomId) {
        super(sid, message, roomId);
        this.type = "TaskMessage";
    }

}
