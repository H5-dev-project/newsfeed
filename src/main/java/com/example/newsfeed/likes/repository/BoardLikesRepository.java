package com.example.newsfeed.likes.repository;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.likes.entity.BaseLike;
import com.example.newsfeed.likes.entity.BoardLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikesRepository extends JpaRepository<BoardLikes, String> {
    Optional<BoardLikes> findByBoardIdAndUsersId(String id, String userId);
}
