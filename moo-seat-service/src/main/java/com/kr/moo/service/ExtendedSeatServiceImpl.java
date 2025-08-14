package com.kr.moo.service;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatErrorCode;
import com.kr.moo.exception.SeatException;
import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExtendedSeatServiceImpl implements ExtendedSeatService {

    private final SeatService seatService;
    private final SeatRepository seatRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SeatResult switchSeat(Long userId, Long storeId, Long seatId) throws SeatException {
        SeatEntity seatEntity = seatRepository.findByStoreEntity_StoreIdAndCurrentUserEntity_UserId(storeId, userId)
                .orElseThrow(() -> new SeatException(SeatErrorCode.FIND_SEAT_FAIL));
        seatService.checkOutSeat(userId, storeId, seatEntity.getSeatId()); // Redis 좌석 해지해도 DB 정보는 트랜잭션 묶여있어 상태는 동일
        return seatService.reserveSeat(userId, storeId, seatId);
    }
}
