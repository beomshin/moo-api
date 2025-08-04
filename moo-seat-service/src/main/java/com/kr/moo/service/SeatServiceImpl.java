package com.kr.moo.service;

import com.kr.moo.dto.SeatResult;
import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public List<SeatResult> findSeatsByStoreId(Long storeId) {
        log.info("◆ 매장 좌석 정보 조회 [DB] : {}", storeId);
        List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
        return seatEntities.stream().map(SeatResult::new).toList();
    }

    @Override
    public SeatResult reserveSeat(Long userId, Long storeId, Long seatId) {
        return null;
    }
}
