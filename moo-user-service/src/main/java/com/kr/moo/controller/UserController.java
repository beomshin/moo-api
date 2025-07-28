package com.kr.moo.controller;

import com.kr.moo.enums.UserCreateResultType;
import com.kr.moo.dto.request.UserCreateRequest;
import com.kr.moo.dto.response.UserCreateResponse;
import com.kr.moo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;




    /**
     * 회원가입
     * @param req 회원가입 정보
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public UserCreateResponse createUser(@Valid @RequestBody UserCreateRequest req){
        UserCreateResultType result = userService.createUser(req);
        
        String code = null;
        String msg = null;
        
        switch (result){
            case SUCCESS ->{
                code = "0000";
                msg = "성공";
            }
            case DUPLICATED_PHONE ->{
                code = "1001";
                msg = "이미 사용 중인 휴대폰 번호입니다.";
            }
            case FAILURE ->{
                code = "9999";
                msg = "실패";
            }
        }
        

        return UserCreateResponse.builder()
                .resultCode(code)
                .resultMsg(msg)
                .build();

    }

}
