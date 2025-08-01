package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    NORMAL_STATUS(0), // 일반 상태
    LEAVE_STATUS(1), // 탈퇴 상태
    QUIT_STATUS(2), // 중지 상태

    ;

    private final Integer value;

    public static UserStatus fromCode(int value) {
        for (UserStatus s : UserStatus.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}
