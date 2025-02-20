package com.example.newsfeed.friend.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//친구 요청 테이블
//친구 a b
@Getter
@Entity
@NoArgsConstructor
@Table(name = "friend")
public class Friend extends BaseEntity {// 친구 요청을 하고 수락을 누르면 해당 행을 삭제하고 서로 유저의 friends List 에 유저 id를 추가해준다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users users;  // 친구 요청을 보낸 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendId", nullable = false)
    private Users friend;  // 친구 요청을 받은 유저

    public Friend(Users users,Users friend){
        this.users = users;
        this.friend = friend;
    }
}
