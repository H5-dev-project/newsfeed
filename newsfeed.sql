-- 유저 테이블
CREATE TABLE `users` (
                         `birth` DATE NOT NULL,
                         `gender` TINYINT NOT NULL,
                         `is_deleted` BIT NOT NULL,
                         `created_at` DATETIME(6),
                         `modified_at` DATETIME(6),
                         `id` VARCHAR(26) NOT NULL,
                         `email` VARCHAR(255) NOT NULL,
                         `image_path` VARCHAR(255) NOT NULL,
                         `password` VARCHAR(255) NOT NULL,
                         `phone` VARCHAR(255) NOT NULL,
                         `username` VARCHAR(255) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_email` (`email`)
);

-- 토큰 테이블
CREATE TABLE `refresh_token` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `users_id` VARCHAR(26),
                                 `refresh_token` VARCHAR(255) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `UK_user_id` (`users_id`),
                                 CONSTRAINT `FK_user_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
);

-- 보드 테이블
CREATE TABLE `boards` (
                          `visibility_type` TINYINT NOT NULL,
                          `created_at` DATETIME(6),
                          `modified_at` DATETIME(6),
                          `id` VARCHAR(26) NOT NULL,
                          `user_id` VARCHAR(26) NOT NULL,
                          `content` LONGTEXT NOT NULL,
                          `images` VARCHAR(255) NOT NULL,
                          `title` VARCHAR(255) NOT NULL,
                          PRIMARY KEY (`id`),
                          CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- 게시글 좋아요 테이블
CREATE TABLE `board_likes` (
                               `created_at` DATETIME(6),
                               `id` BIGINT NOT NULL AUTO_INCREMENT,
                               `modified_at` DATETIME(6),
                               `board_id` VARCHAR(26) NOT NULL,
                               `user_id` VARCHAR(26),
                               PRIMARY KEY (`id`),
                               CONSTRAINT `FK_board_id` FOREIGN KEY (`board_id`) REFERENCES `boards` (`id`),
                               CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- 댓글 테이블
CREATE TABLE `comments` (
                            `created_at` DATETIME(6),
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `modified_at` DATETIME(6),
                            `board_id` VARCHAR(26) NOT NULL,
                            `user_id` VARCHAR(26) NOT NULL,
                            `content` TEXT,
                            PRIMARY KEY (`id`),
                            CONSTRAINT `FK_board_id` FOREIGN KEY (`board_id`) REFERENCES `boards` (`id`),
                            CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- 댓글 좋아요 테이블
CREATE TABLE `comment_likes` (
                                 `comment_id` BIGINT NOT NULL,
                                 `created_at` DATETIME(6),
                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `modified_at` DATETIME(6),
                                 `user_id` VARCHAR(26),
                                 PRIMARY KEY (`id`),
                                 CONSTRAINT `FK_comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
                                 CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- 친구 요청 테이블
CREATE TABLE `friend` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                          `friend_id` VARCHAR(26) NOT NULL,
                          `user_id` VARCHAR(26) NOT NULL,
                          `created_at` DATETIME(6),
                          `modified_at` DATETIME(6),
                          PRIMARY KEY (`id`),
                          CONSTRAINT `FK_friend_id` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`),
                          CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- 친구 수락/거절 관리 테이블
CREATE TABLE `friendship` (
                              `status` SMALLINT NOT NULL,
                              `created_at` DATETIME(6),
                              `id` BIGINT NOT NULL AUTO_INCREMENT,
                              `modified_at` DATETIME(6),
                              `friend_id` VARCHAR(26) NOT NULL,
                              `user_id` VARCHAR(26) NOT NULL,
                              PRIMARY KEY (`id`),
                              CONSTRAINT `FK_friend_id` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`),
                              CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- 스토리 테이블
CREATE TABLE `story` (
                         `visibility_end` DATE,
                         `visibility_start` DATE,
                         `visibility_type` INT NOT NULL,
                         `created_at` DATETIME(6),
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `modified_at` DATETIME(6),
                         `user_id` VARCHAR(26),
                         `content` VARCHAR(255) NOT NULL,
                         PRIMARY KEY (`id`),
                         CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
