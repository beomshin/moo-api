package com.kr.moo.service.impl;

import com.kr.moo.dto.SeatDto;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatErrorCode;
import com.kr.moo.exception.SeatException;
import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.entity.SeatHistoryEntity;
import com.kr.moo.persistence.entity.enums.SeatStatus;
import com.kr.moo.persistence.entity.enums.SeatType;
import com.kr.moo.persistence.repository.SeatRepository;
import com.kr.moo.service.SeatRedisService;
import com.kr.moo.service.SeatService;
import com.kr.moo.service.SeatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatRedisService seatRedisService;
    private final SeatSessionService seatSessionService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SeatResult reserveSeat(SeatDto seatDto) throws SeatException {
        log.info("◆ 좌석 정보 조회 [DB] : [{}]", seatDto.getSeatId());
        SeatEntity seatEntity = seatRepository.findById(seatDto.getSeatId()).orElseThrow(() -> new SeatException(SeatErrorCode.FIND_SEAT_FAIL));

        int isSeatUseUser = seatRepository.countByCurrentUserEntity_UserId(seatDto.getUserId());

        if (isSeatUseUser > 0) {
            log.info("◆ 이미 좌석 사용중인 유저 [DB] : isSeatUseUser [{}]", isSeatUseUser);
            throw new SeatException(SeatErrorCode.USE_STATE_USER);
        }

        Timestamp startAt = new Timestamp(System.currentTimeMillis());
        Timestamp expiredAt = new Timestamp(System.currentTimeMillis() + (3600 + 3));

        if (seatEntity.getSeatType() == SeatType.NORMAL) { // 일반 좌석

            log.info("◆ 일반 좌석 : seatType [{}]", seatEntity.getSeatType());

            if (seatEntity.getSeatStatus() == SeatStatus.USE) {
                log.info("◆ 이미 사용중인 좌석 [DB] : status [{}]", seatEntity.getSeatStatus());
                throw new SeatException(SeatErrorCode.USE_SEAT_STATA);
            }

            // 동시성 제어
            log.info("◆ 좌석 상태 조회 [Redis] : [{}]", seatDto.getSeatId());
            boolean isReserved = seatRedisService.reserveSeat(seatDto.getStoreId(), seatEntity.getSeatNumber());

            if (isReserved) {
                log.info("◆ 이미 사용중인 좌석 [Redis] : status [{}]", seatEntity.getSeatStatus());
                throw new SeatException(SeatErrorCode.USE_SEAT_STATA);
            }

        } else if (seatEntity.getSeatType() == SeatType.FIX) { // 고정 좌석

            log.info("◆ 고정 좌석 : seatType [{}]", seatEntity.getSeatType());

            boolean isNotFixedUser = seatEntity.getFixedUserEntity() == null || seatEntity.getFixedUserEntity().getUserId() != seatDto.getUserId();

            if (isNotFixedUser) { // 고정석 이용자 아님
                Long fixedUserId = seatEntity.getFixedUserEntity() != null ? seatEntity.getFixedUserEntity().getUserId() : null;
                log.info("◆ 고정석 이용자 아님 : db userId [{}], request user Id [{}]", fixedUserId, seatDto.getUserId());
                throw new SeatException(SeatErrorCode.NOT_FIXED_SEAT_USER);
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (now.before(seatEntity.getFixedStartAt()) || now.after(seatEntity.getFixedExpiredAt())) {
                log.info("◆ 비 이용기간 고정 좌석 상태 : {} ~ {}", seatEntity.getFixedStartAt(), seatEntity.getFixedExpiredAt());
                throw new SeatException(SeatErrorCode.NON_USE_PERIOD_USER);
            }
        }

        try {

            log.info("◆ 좌석 예약 업데이트 [DB] : {} ~ {}, seatId [{}], userId, [{}]",  startAt, expiredAt, seatDto.getSeatId(), seatDto.getUserId());
            int isUpdate = seatRepository.updateReservedSeat(seatDto.getUserId(), startAt, expiredAt, SeatStatus.USE, seatDto.getSeatId());

            if (isUpdate > 0) {
                log.info("◆ 좌석 예약 성공 : [{}]", seatDto.getSeatId());
                if (seatDto.isMessageSent()) {
                    seatSessionService.broadcastSeatList(seatDto.getStoreId());
                }
            } else {
                log.info("◆ 좌석 예약 실패 : [{}]", seatDto.getSeatId());
                seatRedisService.releaseSeat(seatDto.getStoreId(), seatEntity.getSeatNumber()); // DB 적용 실패로 예약 비트맵 원복
            }

            return new SeatResult(seatEntity.getSeatNumber(), expiredAt);

        } catch (Exception e) {
            log.error("", e);
            log.error("◆ 좌석 예약 실패 : {}", seatDto.getSeatId());
            seatRedisService.releaseSeat(seatDto.getStoreId(), seatEntity.getSeatNumber()); // DB 적용 실패로 예약 비트맵 원복
            throw new SeatException(SeatErrorCode.RESERVED_SEAT_FAIL);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SeatResult checkOutSeat(SeatDto seatDto) throws SeatException {
        log.info("◆ 좌석 정보 조회 [DB] : [{}]", seatDto.getSeatId());
        SeatEntity seatEntity = seatRepository.findBySeatIdAndStoreEntity_StoreIdAndCurrentUserEntity_UserId(seatDto.getSeatId(), seatDto.getStoreId(), seatDto.getUserId())
                .orElseThrow(() -> new SeatException(SeatErrorCode.FIND_SEAT_FAIL));

        int isUpdate = seatRepository.updateCheckOutSeat(SeatStatus.NORMAL, seatDto.getSeatId());

        if (isUpdate > 0) {
            log.info("◆ 좌석 퇴실 성공 : [{}]", seatDto.getSeatId());
            seatRedisService.releaseSeat(seatDto.getStoreId(), seatEntity.getSeatNumber()); // 좌석 예약 해지

            if (seatDto.isMessageSent()) { // 웹 소켓 세션 리스트에 상태 전송
                seatSessionService.broadcastSeatList(seatDto.getStoreId());
            }

            log.info("◆ 좌석 히스토리 저장 이벤트 발행");
            applicationEventPublisher.publishEvent(SeatHistoryEntity.builder()
                    .seatId(seatEntity.getSeatId())
                    .storeId(seatEntity.getStoreEntity().getStoreId())
                    .currentUserId(seatEntity.getCurrentUserEntity().getUserId())
                    .seatNumber(seatEntity.getSeatNumber())
                    .startAt(seatEntity.getStartAt())
                    .expiredAt(new Timestamp(System.currentTimeMillis()))
                    .seatType(seatEntity.getSeatType())
                    .build());

        } else {
            log.info("◆ 좌석 퇴실 실패 : [{}]", seatDto.getSeatId());
        }

        return new SeatResult();
    }
}
