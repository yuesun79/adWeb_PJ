package com.fudan.se.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudan.se.community.pojo.message.Message;
import com.fudan.se.community.pojo.message.OnlineStatusMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/class/{sid}/{classId}/{positionX}/{positionY}")
@Component
public class ClassWSServer{
    static Log log = LogFactory.getLog(WebSocketServer.class);
    static ObjectMapper mapper = new ObjectMapper();


    private static int onlineCount = 0;
    // 线程安全Set
    private static CopyOnWriteArraySet<ClassWSServer> webSocketServers = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<Integer> onLineIds = new CopyOnWriteArraySet<>();
    private Session session;
    private Integer sid;
    private Integer roomId = 0;
    private String positionX = "";
    private String positionY = "";
    private Integer classId;

    /**
     * 可用来展示在线人数
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("sid")Integer sid,
                       @PathParam("positionX")String positionX,
                       @PathParam("positionY")String positionY,
                       @PathParam("classId")Integer classId) {
        this.session = session;
        this.sid = sid;
        this.positionX = positionX;
        this.positionY = positionY;
        this.classId = classId;
        webSocketServers.add(this);
        onLineIds.add(this.sid);
        addOnlineCount();
        log.info(sid + "上线" + "，当前在线人数为" + getOnlineCount()
                + "-->群发当前在线");
        try {
            // welcome banner
            sendMessage("hello");
            // group online message
            OnlineStatusMessage message = new OnlineStatusMessage(getPeopleInClass(classId), sid, "online",
                    positionX, positionY);
            sendGroupMessage(message, classId, true);
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

        // group online message
        OnlineStatusMessage message = new OnlineStatusMessage(getPeopleInClass(classId), this.sid, "offline",
                this.positionX, this.positionY);
        sendGroupMessage(message, classId, true);

    }

    /**
     * 浏览器端用户给后端发送消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("来自窗口" + sid + "的群发信息" + message);
        Message uMessage = new Message(this.sid, message);
        sendGroupMessage(uMessage,classId,false);
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

    public void sendMessage(Message message) throws IOException {
        String sUsername = MessageWSServer.userService.retrieveUserInfo(message.getSid()).getUsername();
        message.setSUsername(sUsername);
        this.session.getBasicRemote().sendText(mapper.writeValueAsString(message));
    }

    // 发送自定义消息
    public static void sendGroupMessage(Message message,  Integer classId,  boolean self) {
        for (ClassWSServer item : webSocketServers) {
            try {
                // 全部推送
                if (item.classId.equals(classId) && (self | !item.sid.equals(message.getSid())) )
                    item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CopyOnWriteArraySet<Integer> getPeopleInClass(Integer classId) {
        CopyOnWriteArraySet<Integer> res = new CopyOnWriteArraySet<>();
        for (ClassWSServer server : webSocketServers) {
            if (server.classId.equals(classId)) {
                res.add(server.sid);
            }
        }
        return res;
    }

    private static synchronized void subOnlineCount() {
        ClassWSServer.onlineCount--;
    }

    private static synchronized int getOnlineCount() {
        return ClassWSServer.onlineCount;
    }

    private static synchronized void addOnlineCount() {
        ClassWSServer.onlineCount++;
    }
}
