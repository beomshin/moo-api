package com.kr.moo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatErrorCode {

    FIND_SEAT_FAIL("9101", "좌석 정보 조회 실패"),
    RESERVED_SEAT_FAIL("9102", "좌석 예약 실패"),
    USE_SEAT_STATA("9103", "이미 사용중인 좌석"),
    USE_STATE_USER("9104", "이미 이용중인 유저"),
    ALREADY_USE_SEAT("9105", "동일 좌석 선택 불가"),
    NOT_USE_SEAT_STATA("9106", "좌석 미사용중인 유저"),
    NOT_FIXED_SEAT_USER("9107", "고정석 이용자 아님"),
    NON_USE_PERIOD_USER("9108", "고정석 이용불가 시간"),

    ;

    private final String resultCode;
    private final String resultMsg;
}
