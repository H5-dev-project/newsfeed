package com.example.newsfeed.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private final Long id;
    private final String userId;
    private final String title;
    private final String content;
    private final String images;
    private final Byte visibilityType;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime modifiedAt;

    public BoardResponseDto(
            Long id, String userId, String title, String content, String images,
            Byte visibilityType, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.images = images;
        this.visibilityType = visibilityType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
