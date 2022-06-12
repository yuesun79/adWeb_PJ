package com.fudan.se.community.config;

import com.fudan.se.community.controller.MessageWSServer;
import com.fudan.se.community.controller.WebSocketServer;
import com.fudan.se.community.repository.ChatMessageRepository;
import com.fudan.se.community.repository.MessageRepository;
import com.fudan.se.community.service.RoomService;
import com.fudan.se.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@Configuration
public class WebSocketConfig {
//    @Resource
//    private MyWebSocketHandler myWebSocketHandler;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        MessageWSServer.roomService = roomService;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        MessageWSServer.messageRepository = messageRepository;
    }

    @Autowired
    public void setChatMessageRepository(ChatMessageRepository chatMessageRepository) {
        MessageWSServer.chatMessageRepository = chatMessageRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        MessageWSServer.userService = userService;
    }


}
