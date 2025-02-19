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
    private final int boardCount; //공개범위에 따라 개수가 달라져야 함.
    private final int friendCount;
    private final boolean isFriend; //내 아이디와 친구 아이디로 존재하는 인스턴스가 있는지 확인.
    private final List<UsersPageBoardsResponseDto> boards;


    /*
    private final List<> 공개범위에 따른 게시글 사진 리스트를 받아야함., 사진 개수도 계산해서 저장해야 함., 친구관계인지 아닌지, 친구수,
*/
}
