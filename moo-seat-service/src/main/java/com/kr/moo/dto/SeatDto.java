package com.kr.moo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.moo.persistence.entity.SeatEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {

    private Long seatId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredAt;

    private Integer seatNumber;

    private Integer seatType;

    private Integer seatStatus;

    public SeatDto(SeatEntity seatEntity) {
        this.seatId = seatEntity.getSeatId();
        this.startAt = seatEntity.getStartAt();
        this.expiredAt = seatEntity.getExpiredAt();
        this.seatNumber = seatEntity.getSeatNumber();
        this.seatType = seatEntity.getSeatType().getValue();
        this.seatStatus = seatEntity.getSeatStatus().getValue();
    }
}
