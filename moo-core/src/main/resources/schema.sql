DROP TABLE IF EXISTS users;

CREATE TABLE users (
       user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
       user_name VARCHAR(32),
       user_tel VARCHAR(128) UNIQUE ,
       user_tel_last VARCHAR(4),
       user_pwd VARCHAR(128),
       user_birth VARCHAR(8),
       user_join_at TIMESTAMP,
       user_login_type NUMBER,
       user_status NUMBER,
       updated_at TIMESTAMP,
       created_at TIMESTAMP
);


DROP TABLE IF EXISTS stores;

CREATE TABLE stores (
        store_id BIGINT PRIMARY KEY AUTO_INCREMENT, -- 매장 아이디 (PK, 기본키)
        store_name VARCHAR(64) NOT NULL,             -- 매장명
        boss_name VARCHAR(32),                       -- 매장 대표자명
        store_intro VARCHAR(128),                    -- 매장 한 줄 소개
        store_tel VARCHAR(16),                       -- 매장 연락처
        open_at TIMESTAMP,                           -- 매장 오픈일
        open_time TIME,                              -- 매장 영업 시작시간
        close_time TIME,                             -- 매장 영업 종료시간
        is_24h NUMBER(1) DEFAULT 0,                  -- 매장 야간 운영 여부 (0: 미운영, 1: 24시간 운영)
        store_no VARCHAR(16),                        -- 매장 사업자번호
        store_addr VARCHAR(64),                      -- 매장 전체주소 (ex: 서울특별시 마포구 양화로 123)
        store_addr_detail VARCHAR(128),              -- 매장 상세주소 (ex: 3층 301호)
        sido VARCHAR(32),                            -- 매장 주소 시/도 (ex: 서울특별시)
        sigungu VARCHAR(32),                         -- 매장 주소 시/군/구 (ex: 마포구)
        eupmyeondong VARCHAR(32),                    -- 매장 주소 읍/면/동 (ex: 서교동)
        postal_code VARCHAR(16),                     -- 매장 주소 우편번호 (ex: 04038)
        latitude DOUBLE,                             -- 매장 위도
        longitude DOUBLE,                            -- 매장 경도
        seat_count INT DEFAULT 0,                    -- 매장 총 좌석 수
        seat_map_url VARCHAR(128),                   -- 매장 좌석 배치도 URL
        store_status NUMBER(1) DEFAULT 0,            -- 매장 상태 (0: 일반, 1: 탈퇴, 2: 중지)
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 등록일
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 수정일
);