package com.kr.moo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

    JWT_TOKEN_ERROR("9000", "토큰인증 오류")
    ;

    private final String resultCode;
    private final String resultMsg;

}
