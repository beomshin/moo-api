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

    // ConcurrentHashMap은 읽기 작업에는 여러 쓰레드가 동시에 읽을 수 있지만, 쓰기 작업에는 특정 세그먼트 or 버킷에 대한 Lock을 사용
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
