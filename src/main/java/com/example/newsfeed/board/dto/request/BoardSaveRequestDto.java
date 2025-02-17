package com.example.newsfeed.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardSaveRequestDto {

    @NotBlank(message = "제목은 필수 입력값 입니다!")
    private String title;
    @NotBlank(message = "내용은 필수 입력값 입니다!")
    private String content;
    @NotBlank(message = "이미지는 필수 입력값 입니다!")
    private String images;
    @NotBlank(message = "원하는 공개범위를 꼭 설정해주세요!")
    private Byte visibilityType;
}
