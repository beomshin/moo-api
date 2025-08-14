package com.kr.moo.service;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatException;

public interface ExtendedSeatService {

    SeatResult switchSeat(Long userId, Long storeId, Long seatId) throws SeatException;

}
