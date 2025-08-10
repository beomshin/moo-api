package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatType {

    NORMAL(0), // 일반 상태
    FIX(1), // 사용 상태
    ;

    private final Integer value;

    public static SeatType fromCode(int value) {
        for (SeatType s : SeatType.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}
