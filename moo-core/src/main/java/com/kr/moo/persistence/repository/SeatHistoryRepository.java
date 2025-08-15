package com.kr.moo.persistence.repository;

import com.kr.moo.persistence.entity.SeatHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatHistoryRepository extends JpaRepository<SeatHistoryEntity, Long> {
}
