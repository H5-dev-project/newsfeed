package com.example.newsfeed.likes.repository;

import com.example.newsfeed.likes.entity.BoardLikes;
import com.example.newsfeed.likes.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByCommentIdAndUsersId(Long id, String userId);
}
