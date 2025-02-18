package com.example.newsfeed.friend.entity;

import com.example.newsfeed.common.BaseEntity;
import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class FriendShip extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 친구 요청을 보낸 사람
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY) // 친구 요청을 받은 사람
    @JoinColumn(name = "friend_id", nullable = false)
    private Users friend;

    private short status;    //0, 1, 2 = 요청, 승낙, 거절

    public FriendShip(Users user, Users friend, short status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

}

/*
friendship {
    bigInt id PK //long
    varchar(26) user_id FK // 외래키
    varchar(26) friend_id FK // 외래키
    tinyInt status //255
    datetime created_at
}


  -[ ] 특정 사용자를 친구로 추가/삭제 가능
  -[ ] 친구 신청을 받은 경우 상대방의 수락 여부 체크
  -[ ] 친구의 최신 게시물들을 최신순으로 조회


  ### 친구관리
- 특정 사용자를 친구로 `추가` 또는 `삭제` 가능
- 친구 신청을 받은 사용자는 `승락` 또는 `거부` 가능
  */