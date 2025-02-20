package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.service.BoardService;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/api/boards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<BoardResponseDto>> save(
            @Parameter(hidden = true) @UserSession AuthUsers authUsers,
            @RequestPart("board") @io.swagger.v3.oas.annotations.media.Schema(type = "string" , example = "{\"title\" : \"제목\" , \"content\" : \"내용\" , \"visibilityType\" : 1}")
            String boardJson, // String으로 받기
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            // JSON을 BoardSaveRequestDto로 변환
            BoardSaveRequestDto dto = objectMapper.readValue(boardJson, BoardSaveRequestDto.class);
            return ResponseEntity.ok(boardService.save(authUsers, dto, file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/boards")
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>>findAll(@UserSession AuthUsers authUsers) {
        return ResponseEntity.ok(boardService.findAll(authUsers));
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
