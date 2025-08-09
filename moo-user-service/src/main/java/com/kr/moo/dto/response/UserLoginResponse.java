package com.kr.moo.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    /**
     * 로그인 response dto
     */

    private boolean success; // 아이디 없음, 비밀번호 불일치, 로그인 성공 ..

    private String resultMsg;

    private String resultCode;

    // 로그인 성공 시 userId 반환
    private Long userId;
}
