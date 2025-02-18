package com.example.newsfeed.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCountDto {

    private final String boardId;
    private final Long count;

}
