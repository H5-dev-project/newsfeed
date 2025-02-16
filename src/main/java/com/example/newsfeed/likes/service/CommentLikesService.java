package com.example.newsfeed.likes.service;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.CommentLikes;
import com.example.newsfeed.likes.repository.LikesRepository;
import com.example.newsfeed.likes.service.common.AbstractLikesService;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.service.UsersService;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;

@Service
public class CommentLikesService extends AbstractLikesService<CommentLikes, LikesRepository<CommentLikes>> {

    private final CommentService commentService;
    private final UsersService usersService;

    public CommentLikesService(LikesRepository<CommentLikes> likesRepository,
                               CommentService commentService,
                               UsersService usersService) {
        super(likesRepository);
        this.commentService = commentService;
        this.usersService = usersService;
    }

    public ResponseDto<?> addCommentLike(Long commentId, String userId){
        Comment comment = commentService.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        Users user = usersService.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(likeRepository.findByCommentIdAndUserId(comment.getId(), Users.getId()).isPresent()){
            throw new RuntimeException("좋아요는 한 번만 가능합니다.");
        }

        CommentLikes commentLikes = new CommentLikes(user, comment);
        return addLike(commentLikes);
    }

    public ResponseDto<?> cancelCommentLike(Long commentId, Long id){

        commentService.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        CommentLikes commentLikes = likeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("좋아요 등록이 되어있지 않습니다."));

        return cancelLike(commentLikes.getId());
    }
}
