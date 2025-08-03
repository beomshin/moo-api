package com.kr.moo.service;

import com.kr.moo.dto.SeatDto;
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
    public List<SeatDto> findSeatsByStoreId(Long storeId) {
        List<SeatEntity> seatEntities = seatRepository.findByStoreEntity_StoreId(storeId);
        return seatEntities.stream().map(SeatDto::new).toList();
    }
}
