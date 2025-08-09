package com.kr.moo.controller;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.dto.res.ResponseReserveSeat;
import com.kr.moo.exception.SeatException;
import com.kr.moo.jwt.JwtPayload;
import com.kr.moo.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/seat/{seatId}")
    public ResponseEntity<ResponseReserveSeat> seat(
            @PathVariable Long seatId,
            @JwtPayload("userId") Long userId,
            @JwtPayload("storeId") Long storeId
    ) throws SeatException { // 시트 예약
        SeatResult seatResult = seatService.reserveSeat(1L, 1L , seatId);
        return ResponseEntity.ok().body(new ResponseReserveSeat(seatResult));
    }
}
