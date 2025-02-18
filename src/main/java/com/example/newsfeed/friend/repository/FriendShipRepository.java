package com.example.newsfeed.friend.repository;

import com.example.newsfeed.friend.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
    default FriendShip findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }
}