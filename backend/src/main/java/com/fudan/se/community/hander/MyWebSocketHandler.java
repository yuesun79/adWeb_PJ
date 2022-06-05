package com.fudan.se.community.hander;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
public class MyWebSocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("handleMessage");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("afterConnectionClosed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
