package com.example.newsfeed.story.dto;

import com.example.newsfeed.story.entity.Story;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
public class StoryResponseDto {

    private final Long id;
    private final String content;
    private final int visibilityType;
    private final LocalDate visibilityStart;
    private final LocalDate visibilityEnd;

    public StoryResponseDto(Long id, String content, int visibilityType, LocalDate visibilityStart, LocalDate visibilityEnd) {
        this.id = id;
        this.content = content;
        this.visibilityType = visibilityType;
        this.visibilityStart = visibilityStart;
        this.visibilityEnd = visibilityEnd;
    }

    public static StoryResponseDto toDto(Story story){
       return new StoryResponseDto(story.getId(),
               story.getContent(),
               story.getVisibilityType(),
               story.getVisibilityStart(),
               story.getVisibilityEnd());
    }
}
