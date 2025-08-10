package com.kr.moo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    /**
     * 로그인 request dto
     */

    @NotBlank (message = "휴대폰 번호를 입력해주세요.")
    @Size (min = 10, message = "휴대폰 번호는 10자 이상 이어야합니다.")
    private String userTel;

    @NotBlank (message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이여야합니다."
    )
    private String userPwd;

    @NotNull
    private Long storeId; // 로그인할때 매장 아이디
}
