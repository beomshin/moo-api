package com.kr.moo.service;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.exception.SeatException;

public interface SeatService {

    SeatResult reserveSeat(Long userId, Long storeId, Long seatId) throws SeatException;

    SeatResult checkOutSeat(Long userId, Long storeId, Long seatId) throws SeatException;
}
