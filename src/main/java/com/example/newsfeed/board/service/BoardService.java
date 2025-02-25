package com.example.newsfeed.board.service;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.common.FileUtil;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.friend.repository.FriendRepository;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;
    private final FileUtil fileUtil;
    private final FriendRepository friendRepository;

    @Transactional
    public ResponseDto<BoardResponseDto> save(
            AuthUsers authUsers,
            BoardSaveRequestDto dto,
            MultipartFile file
            ) {
        Users user = usersRepository.findById(authUsers.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );

        Board board = new Board(
                user,
                dto.getTitle(),
                dto.getContent(),
                dto.getVisibilityType()
        );

        String imagePath = "basicImage";
        if (file != null && !file.isEmpty()) {
            try {
                // 파일 저장 및 경로 반환
               imagePath = fileUtil.saveBoardImage(file, authUsers.getUserId(), board.getId());
            } catch (IOException e) {
                throw new IllegalStateException("파일 저장 중 오류가 발생했습니다.", e);
            }
        }
        board.updateImage(imagePath);
        boardRepository.save(board);

        BoardResponseDto dtos = BoardResponseDto.toDto(board);
        return ResponseDto.success(dtos);
    }

    @Transactional
    public ResponseDto<List<BoardResponseDto>> findAll(AuthUsers authUsers) {

        List<Board> boards = boardRepository.findAll();

        List<BoardResponseDto> boardDtos = boards.stream()
                .filter(board -> {
                    String boardWriterId = board.getUser().getId();
                    boolean isFriendWithWriter = isFriend(authUsers.getUserId(), boardWriterId);

                    return board.getVisibilityType() == 0 || // 전체 공개
                            (board.getVisibilityType() == 1 && isFriendWithWriter);

                })
                .map(BoardResponseDto::toDto)
                .toList();
        return ResponseDto.success(boardDtos);
    }

    @Transactional
    public ResponseDto<BoardResponseDto> findById(String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        BoardResponseDto dtos = BoardResponseDto.toDto(board);
        return ResponseDto.success(dtos);
    }

    @Transactional
    public ResponseDto<BoardResponseDto> update(String boardId, AuthUsers authUsers, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (!authUsers.getUserId().equals(board.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        board.update(dto.getTitle(),dto.getContent(),dto.getVisibilityType());
        return ResponseDto.success(BoardResponseDto.toDto(board));
    }

    @Transactional
    public void deleteById(String boardId, AuthUsers authUsers) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );
        if (!authUsers.getUserId().equals(board.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }
        fileUtil.deleteBoardImage(authUsers.getUserId(), board.getId());
        boardRepository.delete(board);
    }

    public boolean isFriend(String usersId, String friendId) {
        return friendRepository.existsByUsers_IdAndFriend_Id(usersId, friendId)
                || friendRepository.existsByFriend_IdAndUsers_Id(usersId, friendId);
    }
}
