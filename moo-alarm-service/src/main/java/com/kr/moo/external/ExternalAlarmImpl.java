package com.kr.moo.external;

import com.kr.moo.constants.RedisKey;
import com.kr.moo.dto.AlarmDto;
import com.kr.moo.dto.ExternalAlarmDto;
import com.kr.moo.mongodb.entity.AlarmEntity;
import com.kr.moo.persistence.entity.StoreEntity;
import com.kr.moo.persistence.repository.StoreRepository;
import com.kr.moo.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalAlarmImpl implements ExternalAlarm {

    private final AlarmService alarmService;
    private final StoreRepository storeRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendAlarm(ExternalAlarmDto externalAlarmDto) {
        String storeName = getStoreName(externalAlarmDto.getStoreId());

        AlarmDto alarmDto = AlarmDto.builder()
                .userId(externalAlarmDto.getUserId())
                .alarmType(externalAlarmDto.getAlarmType())
                .createAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        log.info("◆ 요청 알림 메세지 저장 : {}", alarmDto);

        switch (externalAlarmDto.getAlarmType()) {

            case ENTER -> {
                alarmDto.setTitle("[" + storeName + "] 입실");
                alarmDto.setContent("오늘도 화이팅! 좋은 하루 되세요^^");
            }

            case CHECKOUT -> {
                alarmDto.setTitle("[" + storeName + "] 퇴실");
                alarmDto.setContent("오늘도 정말 수고했어요^^");
            }

        }

        AlarmEntity alarmEntity = alarmService.save(alarmDto);
        log.info("◆ 알림 발송 성공 : {}", alarmEntity);

    }

    private String getStoreName(Long storeId) {
        log.info("◆ 상점 아이디 조회 [Redis] {}", storeId);
        String storeName = redisTemplate.opsForValue().get(RedisKey.getStoreKey(storeId));

        if (storeName == null) {
            log.info("◆ 상점 아이디 조회 [DB] {}", storeId);
            Optional<StoreEntity> storeEntity = storeRepository.findById(storeId);

            if (storeEntity.isPresent()) {
                storeName = storeEntity.get().getStoreName();
                redisTemplate.opsForValue().set(RedisKey.getStoreKey(storeId), storeName);
            }

        }

        return storeName;
    }


}
