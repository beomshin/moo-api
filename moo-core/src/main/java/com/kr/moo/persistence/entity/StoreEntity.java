package com.kr.moo.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.moo.persistence.entity.common.BaseEntity;
import com.kr.moo.persistence.entity.converter.OperatingStatusConverter;
import com.kr.moo.persistence.entity.converter.StoreStatusConverter;
import com.kr.moo.persistence.entity.enums.OperatingStatus;
import com.kr.moo.persistence.entity.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@SuperBuilder
@ToString(callSuper=true)
@Entity(name = "stores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class StoreEntity extends BaseEntity {

    /**
     * 테이블명은 stores
     * 엔티티는 store로 통합
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId; // 매장 아이디 (PK, 기본키)

    @Column(name = "store_name", length = 64)
    private String storeName; // 매장명

    @Column(name = "boss_name", length = 32)
    private String bossName; // 매장 대표자명

    @Column(name = "store_intro", length = 128)
    private String storeIntro; // 매장 한 줄 소개

    @Column(name = "store_tel", length = 16)
    private String storeTel; // 매장 연락처

    @Column(name = "open_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp openAt; // 매장 오픈일

    @Column(name = "open_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private Time openTime; // 매장 영업 시작시간

    @Column(name = "close_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private Time closeTime; // 매장 영업 종료시간

    @Column(name = "is_24h")
    @Convert(converter = OperatingStatusConverter.class)
    private OperatingStatus operatingStatus; // 매장 야간 운영 여부	(0: 미운영, 1: 24시간 운영)

    @Column(name = "store_no", length = 16)
    private String storeNo; // 매장 사업자 번호

    @Column(name = "store_addr", length = 64)
    private String storeAddr; // 매장 전체 주소

    @Column(name = "store_addr_detail", length = 128)
    private String storeAddrDetail; // 매장 상세 주소

    @Column(name = "sido", length = 32)
    private String sido; // 매장 주소 시/도

    @Column(name = "sigungu", length = 32)
    private String sigungu; // 매장 주소 시/군/구

    @Column(name = "eupmyeondong", length = 32)
    private String eupmyeondong; // 매장 주소 읍/면/동

    @Column(name = "postal_code", length = 16)
    private String postalCode; // 매장 주소 우편번호

    @Column(name = "latitude")
    private Double latitude; // 매장 위도

    @Column(name = "longitude")
    private Double longitude; // 매장 경도

    @Column(name = "seat_count")
    private Integer seatCount; // 매장 총 좌석 수

    @Column(name = "seat_map_url", length = 128)
    private String seatMapUrl; // 매장 좌석 배치도 URL

    @Column(name = "store_status")
    @Convert(converter = StoreStatusConverter.class)
    private StoreStatus storeStatus; // 매장 상태 (0: 일반, 1: 탈퇴, 2: 중지)

}
