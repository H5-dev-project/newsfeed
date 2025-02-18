package com.example.newsfeed.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String userId;
    private final String boardId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Long id, String userId, String boardId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
