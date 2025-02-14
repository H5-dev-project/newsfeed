package com.example.newsfeed.board.dto.request;

import lombok.Getter;

@Getter
public class BoardSaveRequestDto {

    private String title;
    private String content;
    private String images;
    private Byte visibilityType;
}
