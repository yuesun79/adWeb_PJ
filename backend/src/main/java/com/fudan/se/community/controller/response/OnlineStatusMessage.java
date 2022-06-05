package com.fudan.se.community.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.CopyOnWriteArraySet;

@Data
public class OnlineStatusMessage extends Message{
    String type;
    // current online users
    // : for new user
    CopyOnWriteArraySet<String> onLineIds;

    // last joined/left user
    // : for old user
    String id;
    boolean status;

    public OnlineStatusMessage(CopyOnWriteArraySet<String> onLineIds, String id, boolean status) {
        super(id);
        this.type = "OnlineStatus";
        this.onLineIds = onLineIds;
        this.id = id;
        this.status = status;
    }
}
