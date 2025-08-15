package com.kr.moo.service;

import com.kr.moo.dto.SeatDto;
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
    public SeatResult switchSeat(SeatDto seatDto) throws SeatException {

        /**
         * Redis 변경 트랜잭션을 묶을 수는 없으나 DB 트랜잭션이 묶여있어 분리되어도 이슈 없음
         */

        SeatEntity seatEntity = seatRepository.findByStoreEntity_StoreIdAndCurrentUserEntity_UserId(seatDto.getStoreId(), seatDto.getUserId())
                .orElseThrow(() -> new SeatException(SeatErrorCode.NOT_USE_SEAT_STATA));

        if (seatDto.getSeatId() == seatEntity.getSeatId()) {
            log.info("◆ 좌석이동 동일 좌석 선택 : currentSeatId [{}], switchSeatId [{}]", seatDto.getSeatId(), seatDto.getSeatId());
            throw new SeatException(SeatErrorCode.ALREADY_USE_SEAT);
        }

        SeatDto checkOutSeatDto = SeatDto.builder()
                .seatId(seatEntity.getSeatId())
                .storeId(seatDto.getStoreId())
                .userId(seatDto.getUserId())
                .isMessageSent(false)
                .build();

        seatService.checkOutSeat(checkOutSeatDto); // Redis 좌석 해지해도 DB 정보는 트랜잭션 묶여있어 상태는 동일
        return seatService.reserveSeat(seatDto);
    }
}
