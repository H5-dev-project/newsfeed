package com.example.newsfeed.friend.dto;

import com.example.newsfeed.users.entity.Users;
import lombok.Getter;

@Getter
public class FriendRequestDto {
    //private String userId;
    private Users friend;

    public FriendRequestDto(Users friend) {
        //this.userId = userId;
        this.friend = friend;
    }

}
