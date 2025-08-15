package com.kr.moo.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.moo.persistence.entity.common.BaseEntity;
import com.kr.moo.persistence.entity.converter.SeatTypeConverter;
import com.kr.moo.persistence.entity.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@SuperBuilder
@ToString(callSuper=true)
@Entity(name = "seats_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class SeatHistoryEntity extends BaseEntity {

    /**
     * 테이블명은 seats
     * 엔티티는 seat로 통합
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_history_id")
    private Long seatHistoryId; // 좌석 히스토리 아이디 (PK)

    @Column(name = "seat_id")
    private Long seatId; // 좌석 아이디

    @Column(name = "store_id")
    private Long storeId; // 매장 아이디

    @Column(name = "current_user_id")
    private Long currentUserId; // 이용자 유저 아이디

    @Column(name = "seat_number")
    private Integer seatNumber; // 좌석 번호

    @Column(name = "start_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp startAt; // 좌석 이용 시작시간

    @Column(name = "expired_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp expiredAt; // 좌석 이용 종료시간

    @Column(name = "seat_type")
    @Convert(converter = SeatTypeConverter.class)
    private SeatType seatType; // 좌석 타입 (0: 일반석, 1: 고정석)
}
