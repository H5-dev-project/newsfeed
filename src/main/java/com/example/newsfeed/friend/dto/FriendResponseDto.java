package com.example.newsfeed.friend.dto;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.entity.FriendShip;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendResponseDto {
    private final Long id;
    private final String userId;
    private final String friendId;
<<<<<<< HEAD
    private final LocalDateTime createdAt;

    public FriendResponseDto(Long id, String userId, String friendId, LocalDateTime createdAt){
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.createdAt = createdAt;
    }

    public static FriendResponseDto toDto(FriendShip friendShip){
        return new FriendResponseDto(
                friendShip.getId(),
                friendShip.getUser().getId(), // 요청 보낸 사용자
                friendShip.getFriend().getId(), // 요청 받은 사용자
=======
    private final short status;
    private final LocalDateTime createdAt;

    public FriendResponseDto(Long id, String userId, String friendId, short status, LocalDateTime createdAt){
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static FriendResponseDto toDto(Friend friendShip){
        return new FriendResponseDto(
                friendShip.getId(),
                friendShip.getUsers().getId(), // 요청 보낸 사용자
                friendShip.getFriend().getId(), // 요청 받은 사용자
                friendShip.getStatus(),
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
                friendShip.getCreatedAt()
        );
    }
}
