package com.kr.moo.controller;

import com.kr.moo.persistence.entity.UserEntity;
import com.kr.moo.service.UserListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserListService userListService;

    @GetMapping(value = "/dummy")
    public List<?> findDummy() {
        return userListService.findDummy();
    }

    /**
     * 회원가입 컨트롤러
     * @param params 회원정보
     * @return pk
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Long join(@RequestBody UserEntity params){
        return userListService.saveUser(params);
    }

}
