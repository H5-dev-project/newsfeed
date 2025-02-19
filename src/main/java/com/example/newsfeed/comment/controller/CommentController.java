package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.entity.AuthUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<ResponseDto<CommentResponseDto>> save(@UserSession AuthUsers authUsers, @PathVariable String boardId, @RequestBody CommentSaveRequestDto dto) {
        return ResponseEntity.ok(commentService.save(authUsers.getUserId(), boardId, dto));
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<ResponseDto<List<CommentResponseDto>>> findByBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(commentService.findByBoardId(boardId));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findOne(id));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> update(@UserSession AuthUsers authUsers, @PathVariable Long id, @RequestBody CommentUpdateRequestDto dto) {
        return ResponseEntity.ok(commentService.update(id, authUsers.getUserId(), dto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@UserSession AuthUsers authUsers, @PathVariable Long id) {
        commentService.delete(id, authUsers.getUserId());
        return ResponseEntity.ok().build();
    }


}
