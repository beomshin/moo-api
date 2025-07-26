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
