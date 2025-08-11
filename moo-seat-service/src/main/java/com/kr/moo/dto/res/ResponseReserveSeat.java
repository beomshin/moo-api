package com.kr.moo.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.frame.AbstractResponseSeat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReserveSeat extends AbstractResponseSeat {

    @JsonProperty("expiredAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredAt;

    @JsonProperty("seatNumber")
    private Integer seatNumber;

    public ResponseReserveSeat(SeatResult seatResult) {
        this.seatNumber = seatResult.getSeatNumber();
        this.expiredAt = seatResult.getExpiredAt();
    }
}
