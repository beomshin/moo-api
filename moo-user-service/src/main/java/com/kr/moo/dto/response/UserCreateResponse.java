package com.kr.moo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateResponse {

    /**
     * 회원가입 response dto
     */

    private String resultCode;

    private String resultMsg;


}
