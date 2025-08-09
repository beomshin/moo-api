package com.kr.moo.persistence.entity;

import com.kr.moo.persistence.entity.common.BaseEntity;
import com.kr.moo.persistence.entity.converter.UserLoginTypeConverter;
import com.kr.moo.persistence.entity.converter.UserStatusConverter;
import com.kr.moo.persistence.entity.enums.UserLoginType;
import com.kr.moo.persistence.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Getter
@SuperBuilder
@ToString(callSuper=true)
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class UserEntity extends BaseEntity {

    /**
     * 테이블명은 users
     * 엔티티는 user로 통합
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; // 유저 아이디 (PK, 기본키)

    @Column(name = "user_name")
    private String userName; // 사용자 이름

    @Column(name = "user_tel", length = 128, nullable = false)
    private String userTel; // 전화번호 (암호화)

    @Column(name = "user_tel_last", length = 4)
    private String userTelLast; // 휴대폰 뒷번호 4자리

    @Column(name = "user_pwd", length = 128)
    private String userPwd; // 비밀번호 (암호화)

    @Column(name = "user_birth", length = 8)
    private String userBirth; // 생년월일

    @Column(name = "user_join_at")
    private String userJoinAt; // 최초 가입시간

    @Column(name = "user_login_type")
    @Convert(converter = UserLoginTypeConverter.class)
    private UserLoginType userLoginType; // 로그인 타입 (0: 홈페이지)

    @Column(name = "user_status")
    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus; // 계정 상태 (0: 일반, 1: 탈퇴, 2: 중지)

}
