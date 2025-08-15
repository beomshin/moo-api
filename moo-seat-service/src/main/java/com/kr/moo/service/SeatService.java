package com.kr.moo.service;

import com.kr.moo.dto.SeatDto;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatException;

public interface SeatService {

    SeatResult reserveSeat(SeatDto seatDto) throws SeatException;

    SeatResult checkOutSeat(SeatDto seatDto) throws SeatException;
}
