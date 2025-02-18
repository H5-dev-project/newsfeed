package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.service.BoardService;
import com.example.newsfeed.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<ResponseDto<BoardResponseDto>> save(String userId, @RequestBody BoardSaveRequestDto dto) {
        return ResponseEntity.ok(boardService.save(userId, dto));
    }

    @GetMapping("/api/boards")
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>>findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> findOne(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findById(boardId));
    }

    @PutMapping("/api/boards/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> update(
            @PathVariable Long boardId,
            String userId,
            @RequestBody BoardUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(boardService.update(boardId,userId,dto));
    }

    @DeleteMapping("/api/boards/{boardId}")
    public void delete(@PathVariable Long boardId, String userId) {
        boardService.deleteById(boardId, userId);
    }
}
