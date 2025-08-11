package com.kr.moo.dto;

import com.kr.moo.persistence.entity.SeatEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResult {

    /**
     * seatService 결과 반환 Dto
     */

    private Long seatId;

    private Timestamp startAt;

    private Timestamp expiredAt;

    private Integer seatNumber;

    private Integer seatType;

    private Integer seatStatus;

    public SeatResult(SeatEntity seatEntity) {
        this.seatId = seatEntity.getSeatId();
        this.startAt = seatEntity.getStartAt();
        this.expiredAt = seatEntity.getExpiredAt();
        this.seatNumber = seatEntity.getSeatNumber();
        this.seatType = seatEntity.getSeatType().getValue();
        this.seatStatus = seatEntity.getSeatStatus().getValue();
    }
}
