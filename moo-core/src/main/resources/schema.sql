-- 외래키 무시 설정
SET REFERENTIAL_INTEGRITY FALSE;

-- 삭제 순서 중요
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS stores;
DROP TABLE IF EXISTS users;

SET REFERENTIAL_INTEGRITY TRUE;

CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_name VARCHAR(32),
                       user_tel VARCHAR(128) UNIQUE,
                       user_tel_last VARCHAR(4),
                       user_pwd VARCHAR(128),
                       user_birth VARCHAR(8),
                       user_join_at TIMESTAMP,
                       user_login_type INT,
                       user_status INT,
                       updated_at TIMESTAMP,
                       created_at TIMESTAMP
);

CREATE TABLE stores (
                        store_id BIGINT PRIMARY KEY AUTO_INCREMENT,         -- 매장 아이디 (PK)
                        store_name VARCHAR(64) NOT NULL,                    -- 매장명
                        boss_name VARCHAR(32),                              -- 매장 대표자명
                        store_intro VARCHAR(128),                           -- 매장 한 줄 소개
                        store_tel VARCHAR(16),                              -- 매장 연락처
                        open_at TIMESTAMP,                                  -- 매장 오픈일
                        open_time TIME,                                     -- 매장 영업 시작시간
                        close_time TIME,                                    -- 매장 영업 종료시간
                        is_24h INT DEFAULT 0,                               -- 매장 야간 운영 여부 (0: 미운영, 1: 24시간 운영)
                        store_no VARCHAR(16),                               -- 매장 사업자번호
                        store_addr VARCHAR(64),                             -- 매장 전체주소
                        store_addr_detail VARCHAR(128),                     -- 매장 상세주소
                        sido VARCHAR(32),                                   -- 시/도
                        sigungu VARCHAR(32),                                -- 시/군/구
                        eupmyeondong VARCHAR(32),                           -- 읍/면/동
                        postal_code VARCHAR(16),                            -- 우편번호
                        latitude DOUBLE,                                    -- 위도
                        longitude DOUBLE,                                   -- 경도
                        seat_count INT DEFAULT 0,                           -- 좌석 수
                        seat_map_url VARCHAR(128),                          -- 좌석 배치도 URL
                        store_status INT DEFAULT 0,                         -- 상태
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,     -- 등록일
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP      -- 수정일
);

CREATE TABLE seats (
                       seat_id BIGINT PRIMARY KEY AUTO_INCREMENT,          -- 좌석 아이디 (PK)
                       store_id BIGINT,                                    -- 매장 아이디 (FK)
                       current_user_id BIGINT,                           -- 이용자 유저 아이디
                       fixed_user_id BIGINT,                               -- 고정 좌석 구매자 아이디
                       seat_number INT,                                    -- 좌석 번호
                       start_at TIMESTAMP,                                 -- 시작시간
                       expired_at TIMESTAMP,                               -- 종료시간
                       seat_type INT,                                      -- 좌석 타입
                       seat_status INT,                                    -- 좌석 상태
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,     -- 등록일
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,     -- 수정일
                       CONSTRAINT fk_seats_store_id FOREIGN KEY (store_id) REFERENCES stores(store_id),
                       CONSTRAINT fk_seats_current_user_id FOREIGN KEY (current_user_id) REFERENCES users(user_id),
                       CONSTRAINT fk_seats_fixed_user_id FOREIGN KEY (fixed_user_id) REFERENCES users(user_id)
);
