package com.kr.moo.service;


import com.kr.moo.enums.UserLoginResultType;
import com.kr.moo.util.SHA265Util;
import com.kr.moo.dto.request.UserLoginRequest;
import com.kr.moo.dto.response.UserLoginResponse;
import com.kr.moo.enums.UserCreateResultType;
import com.kr.moo.dto.request.UserCreateRequest;
import com.kr.moo.persistence.entity.StoreEntity;
import com.kr.moo.persistence.entity.UserEntity;
import com.kr.moo.persistence.entity.enums.UserLoginType;
import com.kr.moo.persistence.entity.enums.UserStatus;
import com.kr.moo.persistence.repository.StoreRepository;
import com.kr.moo.persistence.repository.UserRepository;
import com.kr.moo.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final JwtProvider jwtProvider;

    /**
     * 회원가입
     * @param req 회원가입 정보
     * @return
     */
    @Override
    public UserCreateResultType createUser(UserCreateRequest req) {
        try {
            // 1. 휴대폰 중복 검사
            boolean isDuplicated = userRepository.existsByUserTel(SHA265Util.getEncryptText(req.getUserTel()));

            if(isDuplicated){
                // 휴대폰 중복이면 회원가입 x
                return UserCreateResultType.DUPLICATED_PHONE;
            }

            // sha256 암호화 처리 , 정적(static)
            String encrypedTel = SHA265Util.getEncryptText(req.getUserTel()); // 전화번호 암호화
            String encrypedPwd = SHA265Util.getEncryptText(req.getUserPwd()); // 비밀번호 암호화

            // 2. 회원가입 처리
            // Entity로 재정의
            UserEntity user = UserEntity.builder()
                    .userName(req.getUserName())
                    .userPwd(encrypedPwd) // 비밀번호 sha256
                    .userBirth(req.getUserBirth())
                    .userTel(encrypedTel) // 전화번호 sha256
                    .userTelLast(req.getUserTel().substring(req.getUserTel().length() - 4))
                    .userJoinAt(Timestamp.valueOf(LocalDateTime.now()))
                    .userLoginType(UserLoginType.HOMEPAGE_CREATE)
                    .userStatus(UserStatus.NORMAL)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build();

            // 3. db에 저장
            userRepository.save(user);
            return UserCreateResultType.SUCCESS;

        } catch (Exception e){
            return UserCreateResultType.FAILURE;
        }

    }

    /**
     * 로그인 - validation 유효성검사
     * @param req 로그인정보
     * @return
     */
    @Override
    public UserLoginResponse validateLogin(UserLoginRequest req) {
        try {
            // 사용자 존재 여부 확인
            Optional<UserEntity> userOpt = Optional.ofNullable(userRepository.findByUserTel(SHA265Util.getEncryptText(req.getUserTel())));

            // 사용자가 존재하지 않을 때 응답 객체 생성
            // .isEmpty() : 값이 없으면 true
            if(userOpt.isEmpty()){
                return UserLoginResponse.builder()
                        .success(false)
                        .resultCode(UserLoginResultType.NO_USER.getResultCode())
                        .resultMsg(UserLoginResultType.NO_USER.getResultMsg())
                        .build();
            }

            // .get 값이 있으면 꺼내서 사용
            UserEntity user = userOpt.get();

            // 비밀번호 일치 여부 검사
            if(!user.getUserPwd().equals(SHA265Util.getEncryptText(req.getUserPwd()))){
                return UserLoginResponse.builder()
                        .success(false)
                        .resultCode(UserLoginResultType.NO_PASSWORD.getResultCode())
                        .resultMsg(UserLoginResultType.NO_PASSWORD.getResultMsg())
                        .build();
            }

            // 로그인 성공
            return UserLoginResponse.builder()
                    .success(true)
                    .resultCode(UserLoginResultType.SUCCESS.getResultCode())
                    .resultMsg(UserLoginResultType.SUCCESS.getResultMsg())
                    .userId(user.getUserId()) // userId 추가
                    .build();

        } catch (Exception e){

            return UserLoginResponse.builder()
                    .success(false)
                    .resultCode(UserLoginResultType.ERROR.getResultCode())
                    .resultMsg(UserLoginResultType.ERROR.getResultMsg())
                    .build();
        }
    }

    /**
     * 로그인 - 리다이렉트 페이지 생성
     *
     * @param storeId
     * @return
     */
    @Override
    public UserLoginResponse generateLoginResult(Long userId, Long storeId) {
        // 1. jwt 토큰 생성
        String token = jwtProvider.createToken(userId);

        // 3. 상점 정보 조회 및 url 생성
        StoreEntity store = storeRepository.findById(storeId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 상점입니다."));

        // store.enpoint()가 없음
        String redirectUrl = store.getSeatMapUrl();

        return UserLoginResponse.builder()
                .success(true)
                .userId(userId)
                .resultCode(UserLoginResultType.SUCCESS.getResultCode())
                .resultMsg(UserLoginResultType.SUCCESS.getResultMsg())
                .token(token)
                .redirectUrl("http://localhost"+redirectUrl)
                .build();
    }
}
