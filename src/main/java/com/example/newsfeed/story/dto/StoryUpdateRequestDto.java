package com.example.newsfeed.story.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryUpdateRequestDto {

    @NotBlank(message = "내용을 입력해주세요")
    @Schema(description = "스토리 내용", example = "스토리 내용을 입력해주세요")
    private String content;

    private int visibilityType;
}
