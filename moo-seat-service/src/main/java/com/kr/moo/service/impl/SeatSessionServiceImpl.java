package com.kr.moo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.ResponseSeats;
import com.kr.moo.dto.res.frame.ResponseSeat;
import com.kr.moo.manger.SeatSessionManager;
import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.repository.SeatRepository;
import com.kr.moo.service.SeatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatSessionServiceImpl implements SeatSessionService {

    private final SeatSessionManager seatSessionManager;
    private final SeatRepository seatRepository;

    @Override
    public void sendSeatList(WebSocketSession session, Long storeId) throws IOException {
        log.info("◆ 좌석 정보 조회 발송");
        List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
        List<SeatResult> seats = seatEntities.stream().map(SeatResult::new).toList();
        ResponseSeat res = new ResponseSeats(seats);
        String msg = new ObjectMapper().writeValueAsString(res);
        session.sendMessage(new TextMessage(msg));
    }

    @Async
    @Override
    public synchronized void broadcastSeatList(Long storeId) {
        try {

            /**
             * 상태 변경 순서를 위해 synchronized 사용
             * 다중화 서버 경우는 큐 시스템 도입필요
             */

            log.info("◆ 좌석 정보 리스트 브로드 캐스트 비동기 발송");
            List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
            List<SeatResult> seats = seatEntities.stream().map(SeatResult::new).toList();
            ResponseSeat res = new ResponseSeats(seats);
            seatSessionManager.broadcast(new ObjectMapper().writeValueAsString(res));
        } catch (Exception e) {
            log.error("◆ 좌석 정보 리스트 브로드 캐스트 비동기 실패");
            log.error("", e);
        }
    }
}
