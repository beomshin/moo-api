package com.kr.moo.service;

import com.kr.moo.enums.UserCreateResultType;
import com.kr.moo.dto.request.UserCreateRequest;

public interface UserService {


    /**
     * 회원가입 서비스
     * @param req 회원정보
     * @return
     */
    UserCreateResultType createUser(UserCreateRequest req);
}
