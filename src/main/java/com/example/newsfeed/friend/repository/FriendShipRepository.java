package com.example.newsfeed.friend.repository;

<<<<<<< HEAD
import com.example.newsfeed.friend.entity.Friend;
=======
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
import com.example.newsfeed.friend.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

=======
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
    default FriendShip findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }
<<<<<<< HEAD

    List<FriendShip> findAllByUserIdAndStatus(String userId, Short status);

    Short status(short status);
=======
>>>>>>> 34434f07f040114dd1573b1acafc1eb3c2b9eb47
}