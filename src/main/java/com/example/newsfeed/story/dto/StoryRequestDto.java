package com.example.newsfeed.story.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class StoryRequestDto {

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private int visibilityType;

    @NotNull(message = "시작일을 입력해주세요")
    private LocalDate visibilityStart;

    @NotNull(message = "종료일을 입력해주세요")
    private LocalDate visibilityEnd;

    public StoryRequestDto(String content, int visibilityType, LocalDate visibilityStart, LocalDate visibilityEnd) {
        this.content = content;
        this.visibilityType = visibilityType;
        this.visibilityStart = visibilityStart;
        this.visibilityEnd = visibilityEnd;
    }
}
