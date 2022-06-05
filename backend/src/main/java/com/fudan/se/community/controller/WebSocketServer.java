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
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/websocket/{sid}")
@Component
public class WebSocketServer {
    static Log log = LogFactory.getLog(WebSocketServer.class);
    static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    static RoomService roomService;

    private static int onlineCount = 0;
    // 线程安全Set
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<String> onLineIds = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid = "";

    /**
     * 可用来展示在线人数
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid) {
        this.session = session;
        this.sid = sid;
        webSocketServers.add(this);
        onLineIds.add(this.sid);
        addOnlineCount();
        log.info(sid + "上线" + "，当前在线人数为" + getOnlineCount()
                + "-->群发当前在线");
        try {
            // welcome banner
            sendMessage("hello");
            // group online message
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, sid, true);
            sendInfo(message, null);
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
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, sid, false);
            sendInfo(message, null);
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
        log.info("来自窗口" + sid + "的群发信息" + message);
        for (WebSocketServer item : webSocketServers) {
            try {
                Message uMessage = new Message(this.sid, message);
                item.sendMessage(mapper.writeValueAsString(uMessage));
            } catch (IOException e){
                e.printStackTrace();
            }
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
    public static void sendInfo(Message message,String sid) throws IOException{
        // 服务器端收到Message做的一些处理（数据库balabala）
        // 查处处于当前世界的人

        // 服务器端将Message 组装发送 给"特定的"客户端用户
        // 暂时是 给所有在线用户群发消息 的实现
        String receiver = (sid==null) ? "all" : "(sid) " + sid;
        log.info("推送消息-->" + receiver + " 推送内容为" + message);

        String msg = mapper.writeValueAsString(message);

        for (WebSocketServer item : webSocketServers) {
            try {
                // 为null全部推送
                if (sid == null) {
                    item.sendMessage(msg);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(msg);
                }
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
