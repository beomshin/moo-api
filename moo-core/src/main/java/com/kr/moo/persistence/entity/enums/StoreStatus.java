package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {

    NORMAL_STATUS(0), // 일반 상태
    LEAVE_STATUS(1), // 탈퇴 상태
    QUIT_STATUS(2), // 중지 상태

    ;

    private final Integer value;

    public static StoreStatus fromCode(int value) {
        for (StoreStatus s : StoreStatus.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}
