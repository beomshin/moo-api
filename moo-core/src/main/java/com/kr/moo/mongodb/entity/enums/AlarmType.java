package com.kr.moo.mongodb.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {

    COUPON(0), // 쿠폰 구매
    PAY(1), // 이용권 구매
    EXTENSION(2), // 이용권 연장
    EXPIRED(3), // 종료 예정
    FINISHED(4), // 좌석 종료
    OVER_EXTENSION(5), // 좌석 이용 초과
    ENTER(6), // 입실
    CHECKOUT(7), // 퇴실
    SYSTEM(8), // 시스템
    NOTICE(9), // 공지사항

    ;

    private final Integer value;

    public static AlarmType fromCode(int value) {
        for (AlarmType s : AlarmType.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }

}
