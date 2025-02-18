package com.example.newsfeed.friend.dto;

import com.example.newsfeed.friend.entity.FriendShip;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendResponseDto {
    private final Long id;
    private final String user_id;
    private final String friend_id;
    private final short status;
    private final LocalDateTime created_at;

    public FriendResponseDto(Long id, String user_id, String friend_id, short status, LocalDateTime created_at){
        this.id = id;
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.status = status;
        this.created_at = created_at;
    }

    public static FriendResponseDto toDto(FriendShip friendShip){
        return new FriendResponseDto(
                friendShip.getId(),
                friendShip.getUser().getId(), // 요청 보낸 사용자
                friendShip.getFriend().getId(), // 요청 받은 사용자
                friendShip.getStatus(),
                friendShip.getCreatedAt()
        );
    }
}
