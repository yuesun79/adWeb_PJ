package com.fudan.se.community.pojo.message;

import lombok.Data;
import java.util.concurrent.CopyOnWriteArraySet;

@Data
public class OnlineStatusMessage extends Message{
    // current online users
    // : for new user
    CopyOnWriteArraySet<Integer> onLineIds;

    String positionX;
    String positionY;


    public OnlineStatusMessage(CopyOnWriteArraySet<Integer> onLineIds, Integer sid, String message,
                               String positionX, String positionY) {
        super(sid, message);
        this.type = "OnlineStatusMessage";
        this.onLineIds = onLineIds;

        this.positionX = positionX;
        this.positionY = positionY;
    }
}
