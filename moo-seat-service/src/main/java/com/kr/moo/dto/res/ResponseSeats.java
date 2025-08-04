package com.kr.moo.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.frame.AbstractResponseSeat;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString(callSuper = true)
public class ResponseSeats extends AbstractResponseSeat {

    private final List<Seat> seats;

    public ResponseSeats(List<SeatResult> seatResults) {
        this.seats = seatResults.stream().map(Seat::new).toList();
    }

    @Getter
    private class Seat {

        /**
         * 좌석 정보 응답 정보 Dto
         */

        private Long seatId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp startAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp expiredAt;

        private Integer seatNumber;

        private Integer seatType;

        private Integer seatStatus;

        public Seat(SeatResult seatResult) {
            this.seatId = seatResult.getSeatId();
            this.startAt = seatResult.getStartAt();
            this.expiredAt = seatResult.getExpiredAt();
            this.seatNumber = seatResult.getSeatNumber();
            this.seatType = seatResult.getSeatType();
            this.seatStatus = seatResult.getSeatStatus();
        }
    }

}
