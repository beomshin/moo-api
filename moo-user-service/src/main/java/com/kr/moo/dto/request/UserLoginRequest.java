package com.kr.moo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {

    /**
     * 로그인 request dto
     */

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String userTel;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPwd;

    private Long storeId; // 로그인할때 매장 아이디
}
