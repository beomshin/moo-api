package com.kr.moo.service;

import com.kr.moo.dto.SeatDto;

import java.util.List;

public interface SeatService {

    List<SeatDto> findSeatsByStoreId(Long storeId);
}
