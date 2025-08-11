package com.kr.moo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.moo.manger.SeatSessionManager;
import com.kr.moo.service.SeatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SeatListHandler extends TextWebSocketHandler {

    private final SeatSessionManager seatSessionManager;
    private final SeatSessionService seatSessionService;

    /**
     * 2025.08.04
     *
     * 단일 서버 기준으로 구현하였으며 다중화 서버 경우 STOMP 변경 필요
     *
     * @param session
     * @throws Exception
     */

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 클라이언트와 WebSocket 연결이 맺어진 직후 호출

        try {
            seatSessionManager.add(session);
            Long storeId = Long.valueOf(session.getAttributes().get("storeId").toString());
            seatSessionService.sendSeatList(session, storeId);
        } catch (Exception e) {
            log.error("◆ 좌석 정보 조회 중 오류 발생 : ", e);
            seatSessionManager.remove(session);
            session.sendMessage(new TextMessage(
                    new ObjectMapper().writeValueAsString(
                            Map.of("seats", new ArrayList<>())
                    )
            ));
        }

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
