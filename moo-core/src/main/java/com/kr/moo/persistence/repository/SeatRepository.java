package com.kr.moo.persistence.repository;

import com.kr.moo.persistence.entity.SeatEntity;
import com.kr.moo.persistence.entity.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    List<SeatEntity> findByStoreEntity_StoreId(Long storeId);

    Optional<SeatEntity> findBySeatIdAndStoreEntity_StoreIdAndCurrentUserEntity_UserId(Long seatId, Long storeId, Long currentUserId);

    Optional<SeatEntity> findByStoreEntity_StoreIdAndCurrentUserEntity_UserId(Long storeId, Long currentUserId);

    int countByCurrentUserEntity_UserId(Long currentUserId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE seats s
        SET s.currentUserEntity.userId = :currentUserId,
            s.startAt = :startAt,
            s.expiredAt = :expiredAt,
            s.seatStatus = :seatStatus
        WHERE s.seatId = :seatId
    """)
    int updateReservedSeat(@Param("currentUserId") Long currentUserId, @Param("startAt") Timestamp startAt, @Param("expiredAt") Timestamp expiredAt, @Param("seatStatus") SeatStatus seatStatus, @Param("seatId") Long seatId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE seats s
        SET s.currentUserEntity.userId = null,
            s.startAt = null,
            s.expiredAt = null,
               s.seatStatus = :seatStatus
        WHERE s.seatId = :seatId
    """)
    int updateCheckOutSeat(@Param("seatStatus") SeatStatus seatStatus, @Param("seatId") Long seatId);
}
