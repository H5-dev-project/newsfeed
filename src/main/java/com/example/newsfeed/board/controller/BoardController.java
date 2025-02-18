package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.service.BoardService;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.entity.AuthUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<ResponseDto<BoardResponseDto>> save(
            @UserSession AuthUsers authUsers, @RequestPart("board") BoardSaveRequestDto dto,
            @RequestPart(value = "file") MultipartFile file
            ) {
        return ResponseEntity.ok(boardService.save(authUsers, dto, file));
    }

    @GetMapping("/api/boards")
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>>findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> findOne(@PathVariable String boardId) {
        return ResponseEntity.ok(boardService.findById(boardId));
    }

    @PutMapping("/api/boards/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> update(
            @PathVariable String boardId,
            @UserSession AuthUsers authUsers,
            @RequestBody BoardUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(boardService.update(boardId,authUsers,dto));
    }

    @DeleteMapping("/api/boards/{boardId}")
    public void delete(@PathVariable String boardId, @UserSession AuthUsers authUsers) {
        boardService.deleteById(boardId, authUsers);
    }
}
