package com.kr.moo.enums;

public enum UserCreateResultType {
    SUCCESS, // 회원가입 성공
    DUPLICATED_PHONE, // 휴대폰 번호 중복
    FAILURE // 회원가입 실패
}
