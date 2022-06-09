package com.fudan.se.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudan.se.community.controller.response.OnlineStatusMessage;
import com.fudan.se.community.controller.response.Message;
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


@ServerEndpoint(value = "/ws/community/{sid}/{positionX}/{positionY}")
@Component
public class WebSocketServer {
    static Log log = LogFactory.getLog(WebSocketServer.class);
    static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public static RoomService roomService;

    private static int onlineCount = 0;
    // 线程安全Set
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<String> onLineIds = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid = "";
    private Integer roomId = 0;
    private String positionX = "";
    private String positionY = "";

    /**
     * 可用来展示在线人数
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid,
                       @PathParam("positionX")String positionX,
                       @PathParam("positionY")String positionY) {
        this.session = session;
        this.sid = sid;
        this.positionX = positionX;
        this.positionY = positionY;
        webSocketServers.add(this);
        onLineIds.add(this.sid);
        addOnlineCount();
        log.info(sid + "上线" + "，当前在线人数为" + getOnlineCount()
                + "-->群发当前在线");
        try {
            // welcome banner
            sendMessage("hello");
            // group online message
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, sid, true,
                    positionX, positionY, roomId);
            sendGroupMessage(message, true);
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
            sendGroupMessage(message, true);
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
            sendGroupMessage(uMessage,false);
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
    public static void sendGroupMessage(Message message, boolean self) throws IOException{
        String msg = mapper.writeValueAsString(message);
            for (WebSocketServer item : webSocketServers) {
                try {
                    // 全部推送
                    if (self | !item.sid.equals(message.getId()))
                        item.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    private static synchronized int getOnlineCount() {
        return WebSocketServer.onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
}
