package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatStatus {

    NORMAL(0), // 일반 상태
    USE(1), // 사용 상태
    ;

    private final Integer value;

    public static SeatStatus fromCode(int value) {
        for (SeatStatus s : SeatStatus.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}
