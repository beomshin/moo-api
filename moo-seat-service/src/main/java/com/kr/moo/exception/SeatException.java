package com.kr.moo.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class SeatException extends Exception {

    private final String resultCode;
    private final String resultMsg;

    public SeatException(SeatErrorCode seatErrorCode) {
        log.error("◆ 오류 코드 생성 : {}, {}", seatErrorCode.getResultCode(), seatErrorCode.getResultMsg());
        this.resultCode = seatErrorCode.getResultCode();
        this.resultMsg = seatErrorCode.getResultMsg();
    }
}
