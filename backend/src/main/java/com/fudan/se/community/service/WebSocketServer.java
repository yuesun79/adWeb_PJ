package com.fudan.se.community.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    static Log log = LogFactory.getLog(WebSocketServer.class);

    private static int onlineCount = 0;
    // 线程安全Set
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
    private Session session;
    private String sid="";

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
        addOnlineCount();
        log.info("有新的窗口开始监听：" + sid + "，当前在线人数为" + getOnlineCount());
        try {
            sendMessage("hello");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WebSocket IO 异常");
        }
    }

    @OnClose
    public void onClose() {
        webSocketServers.remove(this);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 浏览器端用户给后端发送消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息" + message);
        // 服务器端收到Message做的一些处理（数据库balabala）
        // 查处处于当前世界的人

        // 服务器端将Message 组装发送 给"特定的"客户端用户
        // 暂时是 给所有在线用户群发消息 的实现
        for (WebSocketServer item : webSocketServers) {
            try {
                item.sendMessage(message);
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

    // 群发自定义消息
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException{
        log.info("推送消息到窗口：" + sid + "，推送内容为" + message);
        for (WebSocketServer item : webSocketServers) {
            try {
                // 为null全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
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
