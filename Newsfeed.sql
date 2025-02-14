CREATE TABLE users (
                       id VARCHAR(26) NOT NULL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       birth DATE NOT NULL,
                       phone VARCHAR(20) NOT NULL UNIQUE,
                       gender TINYINT NOT NULL,
                       image VARCHAR(255) NOT NULL DEFAULT 'basicImage',
                       is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
                       modified_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);
CREATE TABLE refresh_token (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               refresh_token VARCHAR(255) NOT NULL,
                               member_id VARCHAR(26) UNIQUE,
                               FOREIGN KEY (member_id) REFERENCES users(id) ON DELETE CASCADE
);