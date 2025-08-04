package com.kr.moo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.frame.ResponseSeat;
import com.kr.moo.dto.res.ResponseSeats;
import com.kr.moo.manger.SeatSessionManager;
import com.kr.moo.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SeatListHandler extends TextWebSocketHandler {

    private final SeatSessionManager seatSessionManager;
    private final SeatService seatService;

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
        seatSessionManager.add(session);

        try {
            log.info("◆ 좌석 정보 조회 세션 접속 성공");
            Long storeId = Long.valueOf(session.getAttributes().get("storeId").toString());
            List<SeatResult> seats = seatService.findSeatsByStoreId(storeId);

            ResponseSeat res = new ResponseSeats(seats);
            String msg = new ObjectMapper().writeValueAsString(res);
            log.info("◆ 좌석 정보 조회 응답 메세지 : [{}]", msg);

            session.sendMessage(new TextMessage(msg));
        } catch (Exception e) {
            log.error("◆ 좌석 정보 조회 중 오류 발생 : ", e);
            String msg = new ObjectMapper().writeValueAsString(
                    Map.of("seats", new ArrayList<>())
            );

            session.sendMessage(new TextMessage(msg));
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
