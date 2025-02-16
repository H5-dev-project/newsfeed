package com.example.newsfeed.likes.repository;

import com.example.newsfeed.likes.entity.BaseLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository<T extends BaseLike> extends JpaRepository<T, Long> {
    Optional<T> findByCommentIdAndUserId(Long id, String userId);
    Optional<T> findByBoardIdAndUserId(Long id, String userId);
}
