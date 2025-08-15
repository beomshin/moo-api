package com.kr.moo.service.impl;

import com.kr.moo.constants.RedisKey;
import com.kr.moo.service.SeatRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatRedisServiceImpl implements SeatRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean reserveSeat(Long storeId, Integer seatNo) {
        return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) conn -> {
            if (Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(RedisKey.getSeatKey(storeId), seatNo))) {
                // 레디스 비트맵 좌석 offset이 active 경우 예약 실패 처리
                return true;
            }

            // 비트맵 좌석이 non active로 active 처리
            redisTemplate.opsForValue().setBit(RedisKey.getSeatKey(storeId), seatNo, true); // 레디스 좌석 active 처리
            return false;
        }));
    }

    @Override
    public void releaseSeat(Long storeId, Integer seatNo) {
        redisTemplate.opsForValue().setBit(RedisKey.getSeatKey(storeId), seatNo, false); // DB 적용 실패로 예약 비트맵 원복
    }

}
