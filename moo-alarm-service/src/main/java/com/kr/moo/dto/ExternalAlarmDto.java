package com.kr.moo.dto;

import com.kr.moo.mongodb.entity.enums.AlarmType;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAlarmDto {

    /**
     * 외부 알림 요청 dto
     */

    private Long userId;

    private Long storeId;

    private AlarmType alarmType;


}
