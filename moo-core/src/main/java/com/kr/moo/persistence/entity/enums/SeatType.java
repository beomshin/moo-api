package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatType {

    NORMAL(0), // 일반석
    FIX(1), // 고정석
    ROOM(2), // 예약석

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
