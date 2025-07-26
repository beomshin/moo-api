package com.kr.moo.controller;

import com.kr.moo.service.UserListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
