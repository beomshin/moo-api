package com.kr.moo.service;

import com.kr.moo.dto.SeatResult;

import java.util.List;

public interface SeatService {

    List<SeatResult> findSeatsByStoreId(Long storeId);

    SeatResult reserveSeat(Long userId, Long storeId, Long seatId);
}
