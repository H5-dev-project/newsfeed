package com.example.newsfeed.board.service;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public ResponseDto save(Long userId, BoardSaveRequestDto dto) {
        User user = User.fromUserId(userId);
        Board board = new Board(
                user,
                dto.getTitle(),
                dto.getContent(),
                dto.getImages(),
                dto.getVisibilityType()
        );
        return new ResponseDto<>(
                board.getId(),
                user.getId(),
                board.getTitle(),
                board.getContent(),
                board.getImages(),
                board.getVisibilityType());
    }
}
