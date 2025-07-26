package com.kr.moo.service;

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
}
