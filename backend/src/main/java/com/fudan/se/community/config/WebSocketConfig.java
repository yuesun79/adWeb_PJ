package com.fudan.se.community.config;

import com.fudan.se.community.controller.WebSocketServer;
import com.fudan.se.community.service.RoomService;
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
    public void setRoomService(RoomService roomService) { WebSocketServer.roomService = roomService;
    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myWebSocketHandler, "/websocket")
//                .addInterceptors(new MySocketHandshakeInterceptor()).setAllowedOrigins("*");
//    }


}
