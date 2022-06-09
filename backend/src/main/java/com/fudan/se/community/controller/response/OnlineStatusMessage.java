package com.fudan.se.community.controller.response;

import lombok.Data;
import java.util.concurrent.CopyOnWriteArraySet;

@Data
public class OnlineStatusMessage extends Message{
    // current online users
    // : for new user
    CopyOnWriteArraySet<String> onLineIds;

    // last joined/left user
    // : for old user

    boolean status;
    String positionX;
    String positionY;


    public OnlineStatusMessage(CopyOnWriteArraySet<String> onLineIds, String id, boolean status,
                               String positionX, String positionY, Integer roomId) {
        super(id, roomId);
        this.type = "OnlineStatus";
        this.onLineIds = onLineIds;
        this.status = status;
        this.positionX = positionX;
        this.positionY = positionY;

    }
}
