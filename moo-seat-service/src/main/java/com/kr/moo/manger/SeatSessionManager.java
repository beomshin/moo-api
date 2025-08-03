package com.kr.moo.manger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SeatSessionManager {

    private final Set<WebSocketSession> socketSessions = ConcurrentHashMap.newKeySet();

    public void add(WebSocketSession session) {
        socketSessions.add(session);
    }

    public void remove(WebSocketSession session) throws IOException {
        session.close();
        socketSessions.remove(session);
    }

    public void error(WebSocketSession session) throws IOException {
        session.close(CloseStatus.SERVER_ERROR);
        socketSessions.remove(session);
    }

    public Set<WebSocketSession> getSessions() {
        return Collections.unmodifiableSet(socketSessions);
    }

    public void broadcast(String message) {
        socketSessions
                .stream()
                .filter(WebSocketSession::isOpen)
                .forEach(session -> {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
