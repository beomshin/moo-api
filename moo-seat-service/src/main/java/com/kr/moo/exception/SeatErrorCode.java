package com.kr.moo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatErrorCode {

    FIND_SEAT_FAIL("9101", "좌석 정보 조회 실패"),
    USE_SEAT_STATA("9102", "사용중인 좌석"),
    NOT_FIXED_SEAT_USER("9103", "고정 좌석 이용자 아님"),
    NON_USE_PERIOD_USER("9104", "비 이용기간 고정 좌석 상태"),
    RESERVED_SEAT_FAIL("9105", "좌석 예약 실패"),
    USE_STATE_USER("9106", "이미 좌석 이용중인 유저"),

    ;

    private final String resultCode;
    private final String resultMsg;
}
