package com.kr.moo.config;

import com.kr.moo.handler.SeatListHandler;
import com.kr.moo.interceptor.JwtHandshakeInterceptor;
import com.kr.moo.manger.SeatSessionManager;
import com.kr.moo.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SeatSessionManager seatSessionManager;
    private final SeatService seatService;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SeatListHandler(seatSessionManager, seatService), "/ws/seats").addInterceptors(jwtHandshakeInterceptor);
    }

}
