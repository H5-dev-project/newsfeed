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

    @PostMapping(value = "/api/boards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<BoardResponseDto>> save(
            @UserSession AuthUsers authUsers,
            @RequestPart("board") @io.swagger.v3.oas.annotations.media.Schema(type = "string", format = "binary") String boardJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        // JSON을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        BoardSaveRequestDto dto;
        try {
            dto = objectMapper.readValue(boardJson, BoardSaveRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 파싱 중 오류 발생: " + e.getMessage());
        }

        return ResponseEntity.ok(boardService.save(authUsers, dto, file));
    }


    @GetMapping("/api/boards")
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>>findAll(BoardSaveRequestDto dto) {
        return ResponseEntity.ok(boardService.findAll(dto));
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
