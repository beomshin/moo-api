package com.kr.moo.service;

import com.kr.moo.enums.UserCreateResultType;
import com.kr.moo.dto.request.UserCreateRequest;
import com.kr.moo.persistence.entity.UserEntity;
import com.kr.moo.persistence.entity.enums.UserLoginType;
import com.kr.moo.persistence.entity.enums.UserStatus;
import com.kr.moo.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    /**
     * 회원가입
     * @param req 회원가입 정보
     * @return
     */
    @Override
    public UserCreateResultType createUser(UserCreateRequest req) {
        try {
            // 1. 휴대폰 중복 검사
            boolean isDuplicated = userRepository.existsByUserTel(req.getUserTel());

            if(isDuplicated){
                // 휴대폰 중복이면 회원가입 x
                return UserCreateResultType.DUPLICATED_PHONE;
            }

            // 2. 회원가입 처리
            // Entity로 재정의
            UserEntity user = UserEntity.builder()
                    .userName(req.getUserName())
                    .userPwd(req.getUserPwd())
                    .userBirth(req.getUserBirth())
                    .userTel(req.getUserTel())
                    .userTelLast(req.getUserTelLast())
                    .userJoinAt(LocalDateTime.now().toString())
                    .userLoginType(UserLoginType.HOMEPAGE_CREATE)
                    .userStatus(UserStatus.NORMAL_STATUS)
                    .build();
            user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

            userRepository.save(user);
            return UserCreateResultType.SUCCESS;

        } catch (Exception e){
            return UserCreateResultType.FAILURE;
        }

    }
}
