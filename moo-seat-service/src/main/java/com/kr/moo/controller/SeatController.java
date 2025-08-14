package com.kr.moo.controller;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.ResponseReserveSeat;
import com.kr.moo.dto.res.frame.AbstractResponseSeat;
import com.kr.moo.dto.res.frame.ResponseSeat;
import com.kr.moo.jwt.JwtPayload;
import com.kr.moo.service.ExtendedSeatService;
import com.kr.moo.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;
    private final ExtendedSeatService extendedSeatService;

    @PostMapping("/{seatId}")
    public ResponseEntity<ResponseSeat> reserveSeat(
            @PathVariable Long seatId,
            @JwtPayload("userId") Integer userId,
            @JwtPayload("storeId") Integer storeId
    ) throws Exception { // 좌석 예약
        SeatResult seatResult = seatService.reserveSeat(Long.valueOf(userId), Long.valueOf(storeId) , seatId);
        return ResponseEntity.ok().body(new ResponseReserveSeat(seatResult));
    }

    @DeleteMapping("/{seatId}")
    public ResponseEntity<ResponseSeat> checkOutSeat(
            @PathVariable Long seatId,
            @JwtPayload("userId") Integer userId,
            @JwtPayload("storeId") Integer storeId
    ) throws Exception { // 좌석 퇴실
        SeatResult seatResult = seatService.checkOutSeat(Long.valueOf(userId), Long.valueOf(storeId) , seatId);
        ResponseSeat responseSeat = new AbstractResponseSeat();
        return ResponseEntity.ok().body(responseSeat);
    }

    @PostMapping("/switch/{seatId}")
    public ResponseEntity<ResponseSeat> switchSeat(
            @PathVariable Long seatId,
            @JwtPayload("userId") Integer userId,
            @JwtPayload("storeId") Integer storeId
    ) throws Exception { // 좌석 예약
        SeatResult seatResult = extendedSeatService.switchSeat(Long.valueOf(userId), Long.valueOf(storeId) , seatId);
        return ResponseEntity.ok().body(new ResponseReserveSeat(seatResult));
    }
}
