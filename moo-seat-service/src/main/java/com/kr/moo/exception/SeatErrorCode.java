package com.kr.moo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatErrorCode {

    FIND_SEAT_FAIL("9101", "좌석 정보 조회 실패"),
    USE_SEAT_STATA("9102", "사용중인 좌석")

    ;

    private final String resultCode;
    private final String resultMsg;
}
