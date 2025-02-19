package com.example.newsfeed.friend.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "friendship")
public class FriendShip extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 친구 요청을 보낸 사람
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY) // 친구 요청을 받은 사람
    @JoinColumn(name = "friendId", nullable = false)
    private Users friend;

    private short status;    //0, 1, 2 = 요청, 승낙, 거절

    public FriendShip(Users user, Users friend, short status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    public FriendShip(short status) {
        this.status = status;
    }
}
