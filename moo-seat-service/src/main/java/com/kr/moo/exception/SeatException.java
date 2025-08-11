package com.kr.moo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeatException extends Exception {

    private final String resultCode;
    private final String resultMsg;

    public SeatException(SeatErrorCode seatErrorCode) {
        this.resultCode = seatErrorCode.getResultCode();
        this.resultMsg = seatErrorCode.getResultMsg();
    }
}
