INSERT INTO users (
    user_id, user_name, user_tel, user_tel_last, user_pwd,
    user_birth, user_join_at, user_login_type, user_status,
    updated_at, created_at
) VALUES
      (1, '홍길동', '01012345678', '5678', 'e99a18c428cb38d5f260853678922e03', '19900101', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (2, '김영희', '01087654321', '4321', '5f4dcc3b5aa765d61d8327deb882cf99', '19850512', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (3, '박철수', '01055556666', '6666', '25f9e794323b453885f5181f1b624d0b', '19930515', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (4, '이민정', '01011223344', '3344', '098f6bcd4621d373cade4e832627b4f6', '19981231', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (5, '최준호', '01099887766', '7766', '21232f297a57a5a743894a0e4a801fc3', '20001201', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (6, '정가영', '01066778899', '8899', '5ebe2294ecd0e0f08eab7690d2a6ee69', '19960214', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (7, '한지민', '01011112222', '2222', '6cb75f652a9b52798eb6cf2201057c73', '19940909', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (8, '유재석', '01033334444', '4444', '8f14e45fceea167a5a36dedd4bea2543', '19780814', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (9, '강호동', '01077778888', '8888', 'b2e98ad6f6eb8508dd6a14cfa704bad7', '19700630', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (10, '서장훈', '01099990000', '0000', '45c48cce2e2d7fbdea1afc51c7c6ad26', '19740312', CURRENT_TIMESTAMP, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO stores (
    store_name, boss_name, store_intro, store_tel, open_at, open_time, close_time, is_24h, store_no, store_addr,
    store_addr_detail, sido, sigungu, eupmyeondong, postal_code, latitude, longitude, seat_count, seat_map_url, store_status)
VALUES
    ('moo', '홍길동', '든든한 한끼를 드립니다', '02-1234-5678', CURRENT_TIMESTAMP, CAST('09:00:00' AS TIME), CAST('22:00:00' AS TIME), 0, '123-45-67890', '서울특별시 강서구 공항대로 293', '지하 1층 105호', '서울특별시', '강서구', '등촌동', '07655', 37.558484, 126.861217, 24, 'https://example.com/seat-map.png', 0);