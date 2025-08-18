package com.kr.moo.external;

import com.kr.moo.dto.ExternalAlarmDto;

public interface ExternalAlarm {

    void sendAlarm(ExternalAlarmDto externalAlarmDto);
}
