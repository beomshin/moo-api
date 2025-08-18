package com.kr.moo.dto;

import com.kr.moo.mongodb.entity.enums.AlarmType;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {

    /**
     * 내부 알림 dto
     */

    private Long userId;

    private String title;

    private String content;

    private AlarmType alarmType;

    private String createAt;

}
