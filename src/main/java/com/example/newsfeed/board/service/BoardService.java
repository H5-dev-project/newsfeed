package com.example.newsfeed.board.service;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ResponseDto<BoardResponseDto> save(String userId, BoardSaveRequestDto dto) {
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        Board board = new Board(
                user,
                dto.getTitle(),
                dto.getContent(),
                dto.getImages(),
                dto.getVisibilityType()
        );
        boardRepository.save(board);

        BoardResponseDto dtos = BoardResponseDto.toDto(board);
        return ResponseDto.success(dtos);
    }

    @Transactional
    public ResponseDto<List<BoardResponseDto>> findAll() {

        List<BoardResponseDto> dtoList = boardRepository.findAll()
                .stream().map(BoardResponseDto::toDto).toList();

        return ResponseDto.success(dtoList);
    }

    @Transactional
    public ResponseDto<BoardResponseDto> findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        BoardResponseDto dtos = BoardResponseDto.toDto(board);
        return ResponseDto.success(dtos);
    }

    @Transactional
    public ResponseDto<BoardResponseDto> update(Long boardId, String userId, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (!userId.equals(board.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        board.update(dto.getTitle(),dto.getContent(),dto.getImages(),dto.getVisibilityType());
        return ResponseDto.success(BoardResponseDto.toDto(board));
    }

    @Transactional
    public void deleteById(Long boardId, String userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );
        if (!userId.equals(board.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }
        boardRepository.delete(board);
    }
}
