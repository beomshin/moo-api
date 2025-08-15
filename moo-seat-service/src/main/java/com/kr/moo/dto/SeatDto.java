package com.kr.moo.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SeatDto {

    private Long seatId;

    private Long storeId;

    private Long userId;

    private boolean isMessageSent; // 세션 메세지 공유 여부

}
