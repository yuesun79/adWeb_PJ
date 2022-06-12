package com.fudan.se.community.pojo.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "room")
public class Message implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    String id;
    Integer sid;
    String sUsername;
    String type;
    String message;


    public Message(Integer sid, String message) {
        this.type = "Message";
        this.sid = sid;
        this.message = message;
    }

}
