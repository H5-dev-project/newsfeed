package com.example.newsfeed.friend.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

//친구 요청 테이블
//승락
//친구 a b
//변수명
@Getter
@Entity
@NoArgsConstructor
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

    private byte status;//0, 1, 2 = 요청, 승낙, 거절

    public Friend(Users users,Users friend, byte status){
        this.users = users;
        this.friend = friend;
        this.status = status;
    }
}
