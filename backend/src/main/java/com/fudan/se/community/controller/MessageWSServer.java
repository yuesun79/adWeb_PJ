package com.fudan.se.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudan.se.community.controller.response.Message;
import com.fudan.se.community.controller.response.OnlineStatusMessage;
import com.fudan.se.community.service.RoomService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/room/{sid}/{roomId}")
@Component
public class MessageWSServer {
    static Log log = LogFactory.getLog(MessageWSServer.class);
    static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public static RoomService roomService;

    private static int onlineCount = 0;
    // 线程安全Set
    private static CopyOnWriteArraySet<MessageWSServer> webSocketServers = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<String> onLineIds = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid = "";
    private Integer roomId;
    private final String positionX = "";
    private final String positionY = "";

    /**
     * 可用来展示在线人数
     * @param session
     * @param sid
     */

    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid,
                       @PathParam("roomId")Integer roomId) {
        log.info("in Message");
        this.session = session;
        this.sid = sid;
        this.roomId = roomId;
        webSocketServers.add(this);
        onLineIds.add(this.sid);
        addOnlineCount();
        log.info(sid + "上线" + "，当前在线人数为" + getOnlineCount()
                + "-->群发当前在线");
        try {
            // welcome banner
            sendMessage("hello Room");
            // group online message
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, sid, true,
                    positionX, positionY, roomId);
            sendGroupMessage(message, this.roomId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WebSocket IO 异常");
        }
    }

    @OnClose
    public void onClose(){
        onLineIds.remove(this.sid);
        webSocketServers.remove(this);
        subOnlineCount();
        log.info(this.sid + "关闭连接 当前在线人数为" + getOnlineCount());

        try {
            // group online message
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, this.sid, false,
                    this.positionX, this.positionY, roomId);
            sendGroupMessage(message, this.roomId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 浏览器端用户给后端发送消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            log.info("来自窗口" + sid + "的群发信息" + message);
            Message uMessage = new Message(this.sid, message, this.roomId);
            sendGroupMessage(uMessage, this.roomId);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    // 服务器端主动推送
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    // 发送自定义消息
    public static void sendGroupMessage(Message message, Integer roomId) throws IOException{
        String msg = mapper.writeValueAsString(message);

        if (roomId == 0) {
            for (MessageWSServer item : webSocketServers) {
                try {
                    // 全部推送
                    if (!item.sid.equals(message.getId())) // && item.roomId.equals(roomId)
                        item.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Integer> users = WebSocketServer.roomService.findUsersInRoom(roomId);
            log.info(users);
            HashSet<Integer> set = new HashSet<>(users);
            for (MessageWSServer item : webSocketServers) {
                if (set.contains(Integer.parseInt(item.sid)) && !item.sid.equals(message.getId())) { // && item.roomId.equals(roomId)
                    log.info("send to" + item.sid);
                    item.sendMessage(msg);
                }
            }
        }
    }

    public static void sendMessageFrom(Message message, Integer roomId) {
        try {
            sendGroupMessage(message, roomId);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private static synchronized void subOnlineCount() {
        MessageWSServer.onlineCount--;
    }

    private static synchronized int getOnlineCount() {
        return MessageWSServer.onlineCount;
    }

    private static synchronized void addOnlineCount() {
        MessageWSServer.onlineCount++;
    }

}
