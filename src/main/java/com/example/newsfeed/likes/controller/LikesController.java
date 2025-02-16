package com.example.newsfeed.likes.controller;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.service.CommentLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final CommentLikesService likesService;

    private AuthUsers getCurrentMemberInfo() {
        AuthUsers authUsers = (AuthUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUsers;
    }

    @PostMapping("/comments/{commentId}/likes")
    public ResponseDto<?> addCommentLikes(@PathVariable Long commentId){
        String userId = getCurrentMemberInfo().getUserId();
        return likesService.addCommentLike(commentId, userId);
    }

    @DeleteMapping("/comments/{commentId}/likes/{id}")
    public ResponseDto<?> addCommentLikes(@PathVariable Long commentId, @RequestParam Long id){
        return likesService.cancelCommentLike(commentId, id);
    }

}
