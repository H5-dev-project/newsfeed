//package com.example.newsfeed.likes.controller;
//
//import com.example.newsfeed.common.dto.ResponseDto;
//import com.example.newsfeed.likes.service.BoardLikesService;
//import com.example.newsfeed.likes.service.CommentLikesService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class LikesController {
//    private final CommentLikesService commentlikesService;
//    private final BoardLikesService boardlikesService;
//
//    private AuthUsers getCurrentMemberInfo() {
//        AuthUsers authUsers = (AuthUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return authUsers;
//    }
//
//    @PostMapping("/comments/{commentId}/likes")
//    public ResponseDto<?> addCommentLike(@PathVariable Long commentId){
//        String userId = getCurrentMemberInfo().getUserId();
//        return commentlikesService.addCommentLike(commentId, userId);
//    }
//
//    @DeleteMapping("/comments/{commentId}/likes/{id}")
//    public ResponseDto<?> cancelCommentLike(@PathVariable Long commentId, @PathVariable Long id){
//        String userId = getCurrentMemberInfo().getUserId();
//        return commentlikesService.cancelCommentLike(commentId, userId);
//    }
//
//    @PostMapping("/boards/{boardId}/likes")
//    public ResponseDto<?> addBoardLike(@PathVariable Long boardId){
//        String userId = getCurrentMemberInfo().getUserId();
//        return boardlikesService.addBoardLike(boardId, userId);
//    }
//
//    @DeleteMapping("/comments/{boardId}/likes/{id}")
//    public ResponseDto<?> cancelBoardLike(@PathVariable Long boardId, @PathVariable Long id){
//        String userId = getCurrentMemberInfo().getUserId();
//        return boardlikesService.cancelBoardLike(boardId, userId);
//    }
//
//}
