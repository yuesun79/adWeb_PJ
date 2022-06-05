package com.fudan.se.community.hander;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Configuration
public class MySocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        log.info("socket beforeHandshake..");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            // 业务处理
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("socket beforeHandshake..");
    }
}
