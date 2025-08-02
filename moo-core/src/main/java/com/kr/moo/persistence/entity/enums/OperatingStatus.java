package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperatingStatus {

    NONE_OPERATING_24H(0), // 24 시간 미운영 상태
    OPERATING_24(1), // 24 시간 운영 상태

    ;

    private final Integer value;

    public static OperatingStatus fromCode(int value) {
        for (OperatingStatus s : OperatingStatus.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}
