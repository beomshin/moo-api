package com.kr.moo.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {

    /**
     * 회원가입 request dto
     */


    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 32, message = "이름은 32자 이내여야 합니다.")
    private String userName;

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Size(min = 10, message = "휴대폰 번호는 10자 이상 이어야합니다.")
    private String userTel;

    @NotBlank(message = "휴대폰 뒷자리는 필수입니다.")
    @Size(min = 4, max = 4, message = "휴대폰 뒷자리는 4자리여야 합니다.")
    private String userTelLast;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이여야합니다."
    )
    private String userPwd;

    @NotBlank(message = "생년월일을 필수입니다.")
    @Pattern(regexp =  "^\\d{8}$", message = "생년월일은 yyyyMMdd 형태로  구성되어야 합니다.")
    private String userBirth;

}
