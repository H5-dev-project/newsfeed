package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.dto.CommentCountDto;
import com.example.newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT NEW com.example.newsfeed.comment.dto.CommentCountDto(c.board.id, count(c)) " +
            "FROM Comment c " +
            "WHERE c.board.id in :boardIds " +
            "GROUP BY c.board.id")
    List<CommentCountDto> countByBoardIds(List<Long> boardIds);

    List<Comment> findByBoardId(String boardId);
}
