package com.kr.moo.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface SeatSessionService {

    void sendSeatList(WebSocketSession session, Long storeId) throws IOException;

    void broadcastSeatList(Long storeId);
}
