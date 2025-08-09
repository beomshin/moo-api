package com.kr.moo.service;

import com.kr.moo.dto.request.UserLoginRequest;
import com.kr.moo.dto.response.UserLoginResponse;
import com.kr.moo.enums.UserCreateResultType;
import com.kr.moo.dto.request.UserCreateRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {


    /**
     * 회원가입 서비스
     * @param req 회원정보
     * @return
     */
    UserCreateResultType createUser(UserCreateRequest req);


    /**
     * 로그인 서비스 - validation 체크
     * @param req 로그인정보
     * @return success, fail ..
     */
    UserLoginResponse validateLogin(UserLoginRequest req);

    /**
     * 로그인 서비스 - 리다이렉트 페이지 + jwt 쿠키 세팅
     *
     * @param storeId
     * @param res
     * @return 리다이렉트 페이지
     */
    String generateRedirectUrlAndSetCookie(Long userId, Long storeId, HttpServletResponse res);


}
