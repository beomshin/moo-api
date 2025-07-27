package com.kr.moo.service;

import com.kr.moo.persistence.entity.UserEntity;
import com.kr.moo.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserListServiceImpl implements UserListService {

    private final UserRepository userRepository;

    @Override
    public List<?> findDummy() {
        return userRepository.findAll();
    }

    /**
     * 회원가입 서비스 구현체
     * @param params 회원정보
     * @return pk
     */
    @Override
    public Long saveUser(UserEntity params) {
        userRepository.save(params);
        return params.getUserId();
    }
}
