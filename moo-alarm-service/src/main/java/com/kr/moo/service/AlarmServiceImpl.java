package com.kr.moo.service;

import com.kr.moo.dto.AlarmDto;
import com.kr.moo.mongodb.entity.AlarmEntity;
import com.kr.moo.mongodb.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

    @Override
    @Transactional
    public AlarmEntity save(AlarmDto alarmDto) {
        log.info("◆ 알림 메세지 몽고디비 저장 시작");
        AlarmEntity entity = AlarmEntity.builder()
                .userId(alarmDto.getUserId())
                .title(alarmDto.getTitle())
                .content(alarmDto.getContent())
                .alarmType(alarmDto.getAlarmType())
                .createAt(alarmDto.getCreateAt())
                .build();
        return alarmRepository.save(entity);
    }
}
