//package com.example.newsfeed.likes.service;
//
//import com.example.newsfeed.comment.entity.Comment;
//import com.example.newsfeed.comment.repository.CommentRepository;
//import com.example.newsfeed.common.dto.ResponseDto;
//import com.example.newsfeed.likes.entity.CommentLikes;
//import com.example.newsfeed.likes.repository.LikesRepository;
//import com.example.newsfeed.likes.service.common.LikesService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class CommentLikesService extends LikesService<CommentLikes> {
//    private final CommentRepository commentRepository;
//    private final UsersRepository usersRepository;
//
//    public CommentLikesService(LikesRepository<CommentLikes> likesRepository,
//                               CommentRepository commentRepository,
//                               UsersRepository usersRepository) {
//        super(likesRepository);
//        this.commentRepository = commentRepository;
//        this.usersRepository = usersRepository;
//    }
//
//    public ResponseDto<?> addCommentLike(Long commentId, String userId){
//        Users user = usersRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
//
//        if(userId.equals(comment.getUsers().getId())){
//            throw new RuntimeException("본인이 작성한 댓글에는 좋아요를 누를 수 없습니다.");
//        }
//
//        if(super.likesRepository.findByCommentIdAndUserId(comment.getId(), user.getId()).isPresent()){
//            throw new RuntimeException("좋아요는 한 번만 가능합니다.");
//        }
//
//        CommentLikes commentLikes = new CommentLikes(user, comment);
//        return addLike(commentLikes);
//    }
//
//    public ResponseDto<?> cancelCommentLike(Long commentId, String userId){
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
//
//        CommentLikes commentLikes = super.likesRepository.findByCommentIdAndUserId(comment.getId(), userId)
//                .orElseThrow(() -> new RuntimeException("좋아요 등록이 되어있지 않습니다."));
//
//        return cancelLike(commentLikes);
//    }
//}
