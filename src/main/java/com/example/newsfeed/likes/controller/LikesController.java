package com.example.newsfeed.likes.controller;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.likes.service.BoardLikesService;
import com.example.newsfeed.likes.service.CommentLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {
    private final CommentLikesService commentlikesService;
    private final BoardLikesService boardlikesService;

    @PostMapping("/{type}/{typeId}")
    public ResponseDto<?> addBoardLike(@UserSession AuthUsers authUser, @PathVariable String type,  @PathVariable Long typeId){
        return switch (type){
            case "boards" -> boardlikesService.addBoardLike(typeId, authUser.getUserId());
            case "comments" -> commentlikesService.addCommentLike(typeId, authUser.getUserId());
            default-> throw new IllegalArgumentException("올바른 타입이 아닙니다.");
        };
    }

    @DeleteMapping("/{type}/{typeId}")
    public ResponseDto<?> cancelBoardLike(@UserSession AuthUsers authUser,  @PathVariable String type,  @PathVariable Long typeId) {
        return switch (type){
            case "boards" -> boardlikesService.cancelBoardLike(typeId, authUser.getUserId());
            case "comments" -> commentlikesService.cancelCommentLike(typeId, authUser.getUserId());
            default -> throw new IllegalArgumentException("올바른 타입이 아닙니다.");
        };
    }

}