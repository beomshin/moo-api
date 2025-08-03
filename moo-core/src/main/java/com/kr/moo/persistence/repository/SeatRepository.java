package com.kr.moo.persistence.repository;

import com.kr.moo.persistence.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    List<SeatEntity> findByStoreEntity_StoreId(Long storeId);
}
