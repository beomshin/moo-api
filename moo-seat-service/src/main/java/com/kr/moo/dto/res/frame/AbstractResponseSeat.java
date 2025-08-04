package com.kr.moo.dto.res.frame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractResponseSeat implements ResponseSeat {

    /**
     * Seat 응답 추상 골격 객체
     */

    private String resultCode = "0000";
    private String resultMsg = "성공";

}
