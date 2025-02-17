package com.example.newsfeed.board.service;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
//    private final CommentRepository commentRepository;

//    @Transactional
//    public ResponseDto<BoardResponseDto> save(Long userId, BoardSaveRequestDto dto) {
//        User user = User.fromUserId(userId);
//        Board board = new Board(
//                user,
//                dto.getTitle(),
//                dto.getContent(),
//                dto.getImages(),
//                dto.getVisibilityType()
//        );
//        Board savedboard = boardRepository.save(board);
//
//        BoardResponseDto dtos = new BoardResponseDto(
//                savedboard.getId(),
//                user.getId(),
//                savedboard.getTitle(),
//                savedboard.getContent(),
//                savedboard.getImages(),
//                savedboard.getVisibilityType());
//        return ResponseDto.success(dtos);
//    }
//
//    public List<ResponseDto> findAll() {
//        List<Board> boards = boardRepository.findAll();
//        List<ResponseDto> dtoList = new ArrayList<>();
//        for (Board board : boards) {
//            dtoList.add(new ResponseDto<>(
//                    board.getId(),
//                    user.getId(),
//                    board.getTitle(),
//                    board.getContent(),
//                    board.getImages(),
//                    board.getVisibilityType()
//            ));
//        }
//        return dtoList;
//    }

//    public ResponseDto findById(Long id) {
//        Board board = boardRepository.findById(id).orElseThrow(
//                ()-> new IllegalAccessException("해당 게시글이 존재하지 않습니다.")
//        );
//        return new ResponseDto(
//                board.getId(),
//                user.getId(),
//                board.getTitle(),
//                board.getContent(),
//                board.getImages(),
//                board.getVisibilityType()
//        );
//    }

//    public ResponseDto update(Long boardId, Long userId, BoardUpdateRequestDto dto) {
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
//        );
//        if (!userId.equals(board.getUser().getId())) {
//            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
//        }
//        board.update(dto.getTitle(),dto.getContent(),dto.getImages(),dto.getVisibilityType());
//        return new ResponseDto(
//                board.getId(),
//                board.getUser().getId(),
//                board.getTitle(),
//                board.getContent(),
//                board.getImages(),
//                board.getVisibilityType()
//        );
//    }
}
