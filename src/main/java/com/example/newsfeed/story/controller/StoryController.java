package com.example.newsfeed.story.controller;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.story.dto.StorySaveRequestDto;
import com.example.newsfeed.story.dto.StoryUpdateRequestDto;
import com.example.newsfeed.story.service.StoryService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {
    private final StoryService storyService;

    @PostMapping("/createStory")
    public ResponseEntity<ResponseDto<?>> createStory(@Parameter(hidden = true) @UserSession AuthUsers authUser,
                                                      @Valid @RequestBody StorySaveRequestDto requestDto) {

        String userId = authUser.getUserId();
        return  ResponseEntity.ok(storyService.createStory(userId, requestDto.getContent()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> updateStory(@Parameter(hidden = true)  @UserSession AuthUsers authUser, @PathVariable Long id, @Valid @RequestBody StoryUpdateRequestDto requestDto) {
        return ResponseEntity.ok(storyService.updateStory(id,
                authUser.getUserId(),
                requestDto.getContent(),
                requestDto.getVisibilityType()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deleteStory(@Parameter(hidden = true)  @UserSession AuthUsers authUser, @PathVariable Long id) {
        return ResponseEntity.ok(storyService.deleteStory(id, authUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.findById(id));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> findByAll() {
        return ResponseEntity.ok(storyService.findAll());
    }

}
