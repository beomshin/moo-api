package com.kr.moo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.moo.dto.SeatDto;
import com.kr.moo.manger.SeatSessionManager;
import com.kr.moo.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SeatListHandler extends TextWebSocketHandler {

    private final SeatSessionManager seatSessionManager;
    private final SeatService seatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 클라이언트와 WebSocket 연결이 맺어진 직후 호출
        seatSessionManager.add(session);

        Long storeId = Long.valueOf(session.getAttributes().get("storeId").toString());
        List<SeatDto> seats = seatService.findSeatsByStoreId(storeId);

        String msg = new ObjectMapper().writeValueAsString(
                Map.of("seats", seats)
        );

        session.sendMessage(new TextMessage(msg));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception { // 연결이 문제 되었을 때 호출
        log.error("◆ SeatListHandler 세션 에러 발생 : ", exception);
        seatSessionManager.error(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { // 연결이 종료되었을 때 호출
        seatSessionManager.remove(session);
    }
}
