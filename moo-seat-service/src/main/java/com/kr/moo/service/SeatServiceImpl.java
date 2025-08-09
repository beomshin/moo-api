package com.kr.moo.service;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatErrorCode;
import com.kr.moo.exception.SeatException;
import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.entity.enums.SeatStatus;
import com.kr.moo.persistence.entity.enums.SeatType;
import com.kr.moo.persistence.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public List<SeatResult> findSeatsByStoreId(Long storeId) {
        log.info("◆ 매장 좌석 정보 조회 [DB] : {}", storeId);
        List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
        return seatEntities.stream().map(SeatResult::new).toList();
    }

    @Override
    @Transactional
    public SeatResult reserveSeat(Long userId, Long storeId, Long seatId) throws SeatException {
        log.info("◆ 좌석 정보 조회 [DB] : {}", seatId);
        SeatEntity seatEntity = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatException(SeatErrorCode.FIND_SEAT_FAIL));

        if (seatEntity.getSeatStatus() == SeatStatus.USE) {
            log.info("◆ 이미 사용중인 좌석 : status {}", seatEntity.getSeatStatus());
            throw new SeatException(SeatErrorCode.USE_SEAT_STATA);
        } else if (seatEntity.getSeatType() == SeatType.FIX) {
            log.info("◆ 고정 좌석 : type {}", seatEntity.getSeatType());
        }

        Timestamp startAt = new Timestamp(System.currentTimeMillis());
        Timestamp expiredAt = new Timestamp(System.currentTimeMillis() + (3600 + 3));

        log.info("◆ 좌석 예약 업데이트 [DB] : {} ~ {}, seatId {}, userId, {}",  startAt, expiredAt, seatId, userId);
        int isUpdate = seatRepository.updateReservedSeat(userId, startAt, expiredAt, SeatStatus.USE, seatId);

        if (isUpdate > 0) {
            log.info("◆ 좌석 예약 성공 : {}", seatId);
        } else {
            log.info("◆ 좌석 예약 실패 : {}", seatId);
        }

        return new SeatResult(seatEntity);
    }
}
