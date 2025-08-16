package com.kr.moo.listener;

import com.kr.moo.persistence.entity.SeatHistoryEntity;
import com.kr.moo.persistence.repository.SeatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatEventListener {

    private final SeatHistoryRepository seatHistoryRepository;

    @Async
    @TransactionalEventListener
    public void saveSeatHistory(SeatHistoryEntity historyEntity) {
        try {
            log.info("◆ 좌석 히스토리 저장 이벤트 수행");
            seatHistoryRepository.save(historyEntity);
        } catch (Exception e) {
            log.error("◆ 좌석 히스토리 저장 이벤트 수행 실패 : ", e);
        }
    }


}
