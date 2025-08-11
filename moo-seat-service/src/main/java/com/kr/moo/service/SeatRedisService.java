package com.kr.moo.service;

public interface SeatRedisService {

    boolean reserveSeat(Long storeId, Integer seatNo);

    void releaseSeat(Long storeId, Integer seatNo);
}
