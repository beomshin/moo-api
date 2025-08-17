package com.kr.moo.mongodb.entity;

import com.kr.moo.mongodb.entity.enums.AlarmType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Document(collection = "alarm")
public class AlarmEntity {

    @Id
    @Field("alarm_id")
    private String alarmId;

    @Field("user_id")
    private Long userId;

    @Field("title")
    private String title;

    @Field("content")
    private String content;

    @Field("alarm_type")
    private AlarmType alarmType;

}
