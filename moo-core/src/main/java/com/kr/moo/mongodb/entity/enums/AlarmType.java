package com.kr.moo.mongodb.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {

    COUPON(0), // 일반 상태
    PAY_OR_EXTENSION(1), // 구매/연장
    EXPIRED(2), // 종료 예정
    FINISHED_OR_OVER_EXTENSION(3), // 종료/초과
    ENTER_OR_CHECKOUT(4), // 입실/퇴실
    SYSTEM(5), // 시스템
    NOTICE(6), // 공지사항

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
