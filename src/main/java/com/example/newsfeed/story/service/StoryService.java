package com.example.newsfeed.story.service;


import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.story.dto.StoryResponseDto;
import com.example.newsfeed.story.entity.Story;
import com.example.newsfeed.story.repository.StoryRepository;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ResponseDto<StoryResponseDto> createStory(String userId, String content){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Story story = new Story(user, content);
        storyRepository.save(story);

        return ResponseDto.success(StoryResponseDto.toDto(story));
    }

    @Transactional
    public ResponseDto<StoryResponseDto> updateStory(Long id, String userId, String content, int visibilityType){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스토리가 존재하지 않습니다."));

        if(!(story.getUsers().getId()).equals(userId)){
            throw new IllegalArgumentException("스토리 작성자만 수정이 가능합니다.");
        }

        story.update(content, visibilityType);
        return ResponseDto.success(StoryResponseDto.toDto(story));
    }

    @Transactional
    public ResponseDto<String> deleteStory(Long id, String userId){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스토리가 존재하지 않습니다."));

        if(!(story.getUsers().getId()).equals(userId)){
            throw new IllegalArgumentException("스토리 작성자만 삭제가 가능합니다.");
        }

        storyRepository.delete(story);
        return ResponseDto.success("성공하였습니다.");
    }

    public ResponseDto<StoryResponseDto> findById(Long id){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스토리가 존재하지 않습니다."));

        if(story.getVisibilityType() == 0){
            throw new IllegalArgumentException("비공개 상태의 스토리입니다.");
        }

        return ResponseDto.success(StoryResponseDto.toDto(story));
    }

    public ResponseDto<List<StoryResponseDto>> findAll(){
        List<StoryResponseDto> storyList = storyRepository.findAllByVisibilityType(1)
                .stream()
                .map(StoryResponseDto :: toDto)
                .toList();

        return ResponseDto.success(storyList.isEmpty() ?  Collections.emptyList() : storyList);
    }

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void isVisibilityExpire(){
        LocalDate now = LocalDate.now();
        List<Story> expiredStories = storyRepository.findByVisibilityEndBeforeAndVisibilityType(now, 1);

        for(Story story : expiredStories){
            story.changeVisibilityType(0);
        }

        storyRepository.saveAll(expiredStories);
    }
}
