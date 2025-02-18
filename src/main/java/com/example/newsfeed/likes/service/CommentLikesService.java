package com.example.newsfeed.likes.service;

import com.example.newsfeed.comment.entity.Comment;
//import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.CommentLikes;
import com.example.newsfeed.likes.repository.CommentLikesRepository;
//import com.example.newsfeed.likes.repository.LikesRepository;
import com.example.newsfeed.likes.service.common.LikesService;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
public class CommentLikesService extends LikesService<CommentLikes> {
//    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;

    public CommentLikesService(CommentLikesRepository commentLikesRepository,
//                               CommentRepository commentRepository,
                               UsersRepository usersRepository) {
        super(commentLikesRepository, usersRepository );
//        this.commentRepository = commentRepository;
        this.commentLikesRepository = commentLikesRepository;
    }

    public ResponseDto<?> addCommentLike(Long commentId, String userId){
//        Users user = usersRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
//
//        return addLike(commentId, comment.getUser().getId(), new CommentLikes(user, comment));
        return ResponseDto.success("성공");
    }

    public ResponseDto<?> cancelCommentLike(Long commentId, String userId){
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
//
////        CommentLikes commentLikes = commentLikesRepository.findByCommentIdAndUsersId(comment.getId(), userId)
////                .orElseThrow(() -> new IllegalArgumentException("좋아요 등록이 되어있지 않습니다."));
//
//        return cancelLike(comment.getId(), userId);
        return ResponseDto.success("성공");
    }
}
