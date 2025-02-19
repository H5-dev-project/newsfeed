package com.example.newsfeed.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageResponseDto {
    private final String email;
    private final String username;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private final LocalDate birth;
    private final String phone;
    private final Byte gender;
    private final String image;
    private final int boardCount;
    private final int friendCount;
    private final List<UsersPageBoardsResponseDto> boards;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime modifiedAt;
/*
    private final List<> 게시글 사진 리스트를 받아야함., 게시물 개수, 친구수,
*/

}
