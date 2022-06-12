package com.fudan.se.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudan.se.community.pojo.message.ChatMessage;
import com.fudan.se.community.pojo.message.Message;
import com.fudan.se.community.pojo.message.OnlineStatusMessage;
import com.fudan.se.community.pojo.message.TaskMessage;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.repository.ChatMessageRepository;
import com.fudan.se.community.repository.MessageRepository;
import com.fudan.se.community.service.RoomService;
import com.fudan.se.community.service.UserService;
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

@ServerEndpoint(value = "/ws/room/{sid}/{roomId}/{positionX}/{positionY}")
@Component
public class MessageWSServer {
    static Log log = LogFactory.getLog(MessageWSServer.class);
    static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public static RoomService roomService;
    @Autowired
    public static MessageRepository messageRepository;
    @Autowired
    public static ChatMessageRepository chatMessageRepository;
    @Autowired
    public static UserService userService;

    private static int onlineCount = 0;
    // 线程安全Set
    private static CopyOnWriteArraySet<MessageWSServer> webSocketServers = new CopyOnWriteArraySet<>();
    private static CopyOnWriteArraySet<Integer> onLineIds = new CopyOnWriteArraySet<>();
    private Session session;
    private Integer sid;
    private Integer roomId;
    private String positionX = "";
    private String positionY = "";

    /**
     * 可用来展示在线人数
     * @param session
     * @param sid
     */

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("sid")Integer sid,
                       @PathParam("roomId")Integer roomId,
                       @PathParam("positionX")String positionX,
                       @PathParam("positionY")String positionY) {
        log.info("in Message");
        this.session = session;
        this.sid = sid;
        this.roomId = roomId;
        this.positionX = positionX;
        this.positionY = positionY;
        webSocketServers.add(this);
        onLineIds.add(this.sid);
        addOnlineCount();
        log.info(sid + "上线" + "，当前在线人数为" + getOnlineCount()
                + "-->群发当前在线");
        try {
            // welcome banner
            sendMessage("hello Room");
            // group online message
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, sid, "online",
                    positionX, positionY);
            sendGroupMessage(message, this.roomId);
            // find unchecked messages from mongodb and send
            List<ChatMessage> chatMessages = chatMessageRepository.findByTidAndAndRoomIdAndStatus(sid, roomId, false);
            for (ChatMessage m : chatMessages) {
                sendMessage(mapper.writeValueAsString(m));
                // message status checked
                m.setStatus(true);
                chatMessageRepository.save(m);
            }
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
            OnlineStatusMessage message = new OnlineStatusMessage(onLineIds, this.sid, "offline",
                    this.positionX, this.positionY);
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
            Message uMessage = new ChatMessage(this.sid, message, this.roomId);
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
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(Message message) throws IOException {
        String sUsername = userService.retrieveUserInfo(message.getSid()).getUsername();
        message.setSUsername(sUsername);
        if (message.getType().equals("ChatMessage") || message.getType().equals("TaskMessage")) {
            String tUsername = userService.retrieveUserInfo(((ChatMessage)message).getTid()).getUsername();
            ((ChatMessage) message).setTUsername(tUsername);
        }

        this.session.getBasicRemote().sendText(mapper.writeValueAsString(message));
    }

    // 发送自定义消息
    public static void sendGroupMessage(Message message, Integer roomId) throws IOException{
        String msg = mapper.writeValueAsString(message);
        boolean storageFlag = !message.getType().equals("OnlineStatusMessage");
        boolean taskFlag = message.getType().equals("TaskMessage");

        // 发送给同一个房间的User（根据数据库查询）
        List<User> users = roomService.findUsersInRoom(roomId);
        log.info(users);
        if (taskFlag) {
            // 当前团队人数
            ((TaskMessage)message).setTeamMem(users.size());
            Integer teamSize = roomService.getTeamSize_roomId(roomId);
            // 组队总需人数
            ((TaskMessage)message).setTeamSize(teamSize);
        }
        HashSet<Integer> userIdSet = new HashSet<>();
        for (User user : users) {
            userIdSet.add(user.getId());
        }
        // online : send through websocket + save status true
        for (MessageWSServer item : webSocketServers) {
            if (userIdSet.contains(item.sid) && !item.sid.equals(message.getSid())) { // && item.roomId.equals(roomId)
                log.info("send to " + item.sid);
                if (storageFlag) {
                    message.setId(null);
                    ((ChatMessage) message).setStatus(true);
                    ((ChatMessage) message).setTid(item.sid);
                    userIdSet.remove(item.sid);
                    log.info(message);
                    messageRepository.insert((ChatMessage) message);
                }
                item.sendMessage(message);
            }
        }
        // offline : save status false
        if (storageFlag) {
            for (Integer i : userIdSet) {
                if (!i.equals(message.getSid())) {
                    message.setId(null);
                    ((ChatMessage) message).setStatus(false);
                    ((ChatMessage) message).setTid(i);
                    messageRepository.insert((ChatMessage) message);
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
