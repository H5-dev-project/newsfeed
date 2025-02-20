package com.example.newsfeed.story.dto;

import com.example.newsfeed.story.entity.Story;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class StoryResponseDto {

    private final Long id;
    private final String content;
    private final int visibilityType;
    private final LocalDateTime visibilityStart;
    private final LocalDateTime visibilityEnd;

    public StoryResponseDto(Long id, String content, int visibilityType, LocalDateTime visibilityStart, LocalDateTime visibilityEnd) {
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
