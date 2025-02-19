package com.example.newsfeed.friend.dto;

import com.example.newsfeed.users.entity.Users;
import lombok.Getter;

@Getter
public class FriendRequestDto {
<<<<<<< HEAD
    private String friend;

//    public FriendRequestDto(Users friend) {
//        this.friend = friend;
//    }
=======
    //private String userId;
    private Users friend;

    public FriendRequestDto(Users friend) {
        //this.userId = userId;
        this.friend = friend;
    }
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47

}
