package com.example.newsfeed.friend.dto;

import com.example.newsfeed.users.entity.Users;
import lombok.Getter;

@Getter
public class FriendRequestDto {
    private String user_id;
    private Users friend;

    public FriendRequestDto(String user_id, Users friend) {
        this.user_id = user_id;
        this.friend = friend;
    }

}
