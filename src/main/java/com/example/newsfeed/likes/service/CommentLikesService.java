package com.example.newsfeed.likes.service;


import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.CommentLikes;
import com.example.newsfeed.likes.repository.CommentLikesRepository;
import com.example.newsfeed.likes.service.common.LikesService;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentLikesService extends LikesService<CommentLikes> {
    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;

    public CommentLikesService(CommentLikesRepository commentLikesRepository,
                               CommentRepository commentRepository,
                               UsersRepository usersRepository) {
        super(commentLikesRepository, usersRepository );
        this.commentRepository = commentRepository;
        this.commentLikesRepository = commentLikesRepository;
    }

    public ResponseDto<?> addCommentLike(Long commentId, String userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        return addLike(comment.getUser().getId(), String.valueOf(comment.getId()), new CommentLikes(user, comment));
    }

    public ResponseDto<?> cancelCommentLike(Long commentId, String userId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        return cancelLike(String.valueOf(comment.getId()), userId);
    }
}
