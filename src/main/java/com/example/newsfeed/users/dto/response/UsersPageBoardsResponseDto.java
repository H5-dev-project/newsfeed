package com.example.newsfeed.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UsersPageBoardsResponseDto {
    private final String boardId;
    private final String boardImages;
}
