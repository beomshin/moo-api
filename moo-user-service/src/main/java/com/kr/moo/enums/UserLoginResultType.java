package com.kr.moo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
public enum UserLoginResultType {

    NO_USER("9001","존재하지 않는 사용자입니다."),
    NO_PASSWORD("9002","비밀번호가 일치하지 않습니다."),
    SUCCESS("0000","로그인 성공"),
    ERROR("9009","에러발생");


    private final String resultCode;
    private final String resultMsg;

}
