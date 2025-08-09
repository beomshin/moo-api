package com.kr.moo.controller;

import com.kr.moo.dto.request.UserLoginRequest;
import com.kr.moo.dto.response.UserLoginResponse;
import com.kr.moo.enums.UserCreateResultType;
import com.kr.moo.dto.request.UserCreateRequest;
import com.kr.moo.dto.response.UserCreateResponse;
import com.kr.moo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor // 생성자 자동생성
@RequestMapping(value = "/user") // /user로 시작하는 url처리
public class UserController {

    private final UserService userService; // 회원관련 서비스

    /**
     * 회원가입 api
     * @param req 회원가입 정보
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public UserCreateResponse createUser(@Valid @RequestBody UserCreateRequest req){
        // 1. 회원가입 로직 실행
        UserCreateResultType result = userService.createUser(req);

        // 2. 결과 메시지 설정
        String code = null;
        String msg = null;
        
        switch (result){
            case SUCCESS ->{
                code = "0000"; msg = "성공";
            }
            case DUPLICATED_PHONE ->{
                code = "1001"; msg = "이미 사용 중인 휴대폰 번호입니다.";
            }
            case FAILURE ->{
                code = "9999"; msg = "실패";
            }
        }

        // 3. 응답 객체 생성
        return UserCreateResponse.builder()
                .resultCode(code)
                .resultMsg(msg)
                .build();

    }


    /**
     * 로그인 api
     * @param req 로그인 정보
     * @param response
     * @return
     */
    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody UserLoginRequest req, HttpServletResponse response){

        // 1. validation 체크 (로그인 정보 검증 )
        UserLoginResponse result = userService.validateLogin(req);

        if(!result.isSuccess()){
            // 로그인 실패 시 에러 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(result.getResultMsg());
        }

        // 2. 성공이면 jwt 쿠키 생성 + 페이지 url 반환
        String redirectUrl = userService.generateRedirectUrlAndSetCookie(result.getUserId(), req.getStoreId(), response);


        // 3. 리다이렉트 url 응답
        return ResponseEntity.ok(redirectUrl);
    }

}
