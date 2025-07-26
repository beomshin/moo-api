package com.kr.moo.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserLoginType {

    HOMEPAGE_CREATE(0), // 홈페이지 가입 유저

    ;

    private final Integer value;

    public static UserLoginType fromCode(int value) {
        for (UserLoginType s : UserLoginType.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        return null;
    }
}
