package com.example.newsfeed.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UsersPageResponseDto {
    private final String username;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private final LocalDate birth;
    private final Byte gender;
    private final String image;
    private final int boardCount;
    private final int friendCount;
    private final boolean isFriend;
    private final List<UsersPageBoardsResponseDto> boards;

}
