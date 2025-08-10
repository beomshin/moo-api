package com.kr.moo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.moo.constants.RedisKey;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.ResponseSeats;
import com.kr.moo.dto.res.frame.ResponseSeat;
import com.kr.moo.exception.SeatErrorCode;
import com.kr.moo.exception.SeatException;
import com.kr.moo.manger.SeatSessionManager;
import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.entity.enums.SeatStatus;
import com.kr.moo.persistence.entity.enums.SeatType;
import com.kr.moo.persistence.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final SeatSessionManager seatSessionManager;

    @Override
    public List<SeatResult> findSeatsByStoreId(Long storeId) {
        log.info("◆ 매장 좌석 정보 조회 [DB] : {}", storeId);
        List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
        return seatEntities.stream().map(SeatResult::new).toList();
    }

    @Override
    @Transactional
    public SeatResult reserveSeat(Long userId, Long storeId, Long seatId) throws SeatException {
        log.info("◆ 좌석 정보 조회 [DB] : [{}]", seatId);
        SeatEntity seatEntity = seatRepository.findById(seatId).orElseThrow(() -> new SeatException(SeatErrorCode.FIND_SEAT_FAIL));

        Timestamp startAt = new Timestamp(System.currentTimeMillis());
        Timestamp expiredAt = new Timestamp(System.currentTimeMillis() + (3600 + 3));

        if (seatEntity.getSeatType() == SeatType.NORMAL) { // 일반 좌석

            if (seatEntity.getSeatStatus() == SeatStatus.USE) {
                log.info("◆ 이미 사용중인 좌석 [DB] : status [{}]", seatEntity.getSeatStatus());
                throw new SeatException(SeatErrorCode.USE_SEAT_STATA);
            }

            // 동시성 제어
            log.info("◆ 좌석 상태 조회 [Redis] : [{}]", seatId);
            boolean isReserved = Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) conn -> {
                if (Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(RedisKey.getSeatKey(storeId), seatEntity.getSeatNumber()))) {
                    // 레디스 비트맵 좌석 offset이 active 경우 예약 실패 처리
                    return true;
                }

                // 비트맵 좌석이 non active로 active 처리
                redisTemplate.opsForValue().setBit(RedisKey.getSeatKey(storeId), seatEntity.getSeatNumber(), true); // 레디스 좌석 active 처리
                return false;
            }));

            if (isReserved) {
                log.info("◆ 이미 사용중인 좌석 [Redis] : status [{}]", seatEntity.getSeatStatus());
                throw new SeatException(SeatErrorCode.USE_SEAT_STATA);
            }

        } else if (seatEntity.getSeatType() == SeatType.FIX) { // 고정 좌석

            log.info("◆ 고정 좌석 : type [{}]", seatEntity.getSeatType());

            boolean isNotFixedUser = seatEntity.getFixedUserEntity() == null || seatEntity.getFixedUserEntity().getUserId() != userId;

            if (isNotFixedUser) { // 고정석 이용자 아님
                Long fixedUserId = seatEntity.getFixedUserEntity() != null ? seatEntity.getFixedUserEntity().getUserId() : null;
                log.info("◆ 고정석 이용자 아님 : db userId [{}], request user Id [{}]", fixedUserId, userId);
                throw new SeatException(SeatErrorCode.NOT_FIXED_SEAT_USER);
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (now.before(seatEntity.getFixedStartAt()) || now.after(seatEntity.getFixedExpiredAt())) {
                log.info("◆ 비 이용기간 고정 좌석 상태 : {} ~ {}", seatEntity.getFixedStartAt(), seatEntity.getFixedExpiredAt());
                throw new SeatException(SeatErrorCode.NON_USE_PERIOD_USER);
            }
        }

        try {

            log.info("◆ 좌석 예약 업데이트 [DB] : {} ~ {}, seatId [{}], userId, [{}]",  startAt, expiredAt, seatId, userId);
            int isUpdate = seatRepository.updateReservedSeat(userId, startAt, expiredAt, SeatStatus.USE, seatId);

            if (isUpdate > 0) {
                log.info("◆ 좌석 예약 성공 : [{}]", seatId);
                List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
                ResponseSeat res = new ResponseSeats(seatEntities.stream().map(SeatResult::new).toList());
                seatSessionManager.broadcast(new ObjectMapper().writeValueAsString(res));
            } else {
                log.info("◆ 좌석 예약 실패 : [{}]", seatId);
                redisTemplate.opsForValue().setBit(RedisKey.getSeatKey(storeId), seatEntity.getSeatNumber(), false); // DB 적용 실패로 예약 비트맵 원복
            }

            return new SeatResult(seatEntity);

        } catch (Exception e) {
            log.error("", e);
            log.error("◆ 좌석 예약 실패 : {}", seatId);
            redisTemplate.opsForValue().setBit(RedisKey.getSeatKey(storeId), seatEntity.getSeatNumber(), false); // DB 적용 실패로 예약 비트맵 원복
            throw new SeatException(SeatErrorCode.RESERVED_SEAT_FAIL);
        }

    }
}
