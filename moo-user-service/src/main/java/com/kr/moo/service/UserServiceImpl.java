package com.kr.moo.service;


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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
            boolean isDuplicated = userRepository.existsByUserTel(req.getUserTel());

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
                    .userTelLast(req.getUserTelLast())
                    .userJoinAt(LocalDateTime.now().toString())
                    .userLoginType(UserLoginType.HOMEPAGE_CREATE)
                    .userStatus(UserStatus.NORMAL_STATUS)
                    .build();
            user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

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
            // 리포지토리에서 핸드폰번호 찾기
            Optional<UserEntity> userOpt = Optional.ofNullable(userRepository.findByUserTel(SHA265Util.getEncryptText(req.getUserTel())));

            // 핸드폰 번호 없을 시 실패
            // .isEmpty() : 값이 없으면 true
            if(userOpt.isEmpty()){
                return UserLoginResponse.builder()
                        .success(false)
                        .resultMsg("존재하지 않는 사용자입니다.")
                        .resultCode("1111")
                        .build();
            }

            // .get 값이 있으면 꺼내서 사용
            UserEntity user = userOpt.get();

            // 비밀번호 일치 여부 검사
            if(!user.getUserPwd().equals(SHA265Util.getEncryptText(req.getUserPwd()))){
                return UserLoginResponse.builder()
                        .success(false)
                        .resultMsg("비밀번호가 일치하지 않습니다.")
                        .resultCode("2222")
                        .build();
            }

            // 로그인 성공
            return UserLoginResponse.builder()
                    .success(true)
                    .resultMsg("로그인 성공")
                    .resultCode("0000")
                    .userId(user.getUserId()) // userId 추가
                    .build();

        } catch (Exception e){

            return UserLoginResponse.builder()
                    .success(false)
                    .resultMsg("에러발생")
                    .resultCode("9999")
                    .build();
        }
    }

    /**
     * 로그인 - 리다이렉트 페이지 생성
     *
     * @param storeId
     * @param res
     * @return
     */
    @Override
    public String generateRedirectUrlAndSetCookie(Long userId, Long storeId, HttpServletResponse res) {
        // 1. jwt 생성
        String token = jwtProvider.createToken(userId);

        // 2. 쿠키 설정
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(60*60); // 쿠키 유효시간 1시간
        cookie.setHttpOnly(true); // js 접근 방지
        cookie.setPath("/"); // 전체 경로에 적요

        res.addCookie(cookie); // 응답에 쿠키 추가

        // 3. 상점 정보 조회 -> url 생성
        StoreEntity store = storeRepository.findById(storeId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 상점입니다."));

        // store.enpoint()가 없음
//        String endpoint = store.getEndpoint();
        String endpoint = "test";
        return "http://" + endpoint;

    }

}
