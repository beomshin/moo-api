package com.kr.moo.service;

import com.kr.moo.persistence.entity.UserEntity;

import java.util.List;

public interface UserListService {

    List<?> findDummy();

    /**
     * 회원가입 서비스
     * @param params 회원정보
     * @return pk
     */
    Long saveUser(UserEntity params);
}
