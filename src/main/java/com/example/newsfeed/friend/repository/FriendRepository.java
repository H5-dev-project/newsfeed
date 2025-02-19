package com.example.newsfeed.friend.repository;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.users.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend, Long> {
    default Friend findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // 기존 친구 요청 여부 확인 (중복 방지)
    public boolean existsByUsersAndFriend(Users users, Users friend);

    Optional<Friend> findByFriendIdAndUsersId(String friendId, String userId);

//    select * from friend where friend_id = "친구신" And user_id = "나";
}
