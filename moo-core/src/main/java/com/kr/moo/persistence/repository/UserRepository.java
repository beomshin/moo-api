package com.kr.moo.persistence.repository;

import com.kr.moo.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * 휴대폰 번호 중복 검사
     * @param userTel
     * @return
     */
    boolean existsByUserTel(String userTel);
}
