package com.kr.moo.service;

import com.kr.moo.dto.AlarmDto;
import com.kr.moo.mongodb.entity.AlarmEntity;

public interface AlarmService {

    AlarmEntity save(AlarmDto alarmDto);
}
