package com.kr.moo.service;

import com.kr.moo.dto.SeatDto;
import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatException;

public interface ExtendedSeatService {

    SeatResult switchSeat(SeatDto seatDto) throws SeatException;

}
