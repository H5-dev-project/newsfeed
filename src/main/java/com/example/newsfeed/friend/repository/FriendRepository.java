package com.example.newsfeed.friend.repository;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.users.entity.Users;
<<<<<<< HEAD
import org.aspectj.weaver.ast.And;
=======
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

<<<<<<< HEAD
import java.util.Optional;

=======
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
public interface FriendRepository extends JpaRepository<Friend, Long> {
    default Friend findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }
    // 기존 친구 요청 여부 확인 (중복 방지)
    public boolean existsByUsersAndFriend(Users users, Users friend);
<<<<<<< HEAD

    Optional<Friend> findByFriendIdAndUsersId(String friendId, String userId);

//    select * from friend where friend_id = "친구신" And user_id = "나";
=======
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
}
