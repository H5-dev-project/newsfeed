package com.example.newsfeed.likes.service;


import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.BoardLikes;
import com.example.newsfeed.likes.repository.BoardLikesRepository;
import com.example.newsfeed.likes.service.common.LikesService;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardLikesService extends LikesService<BoardLikes> {
    private final BoardRepository boardRepository;
    private final BoardLikesRepository boardLikesRepository;

    public BoardLikesService(BoardLikesRepository boardLikesRepository,
                             UsersRepository usersRepository,
                             BoardRepository boardRepository) {
        super(boardLikesRepository, usersRepository);
        this.boardRepository = boardRepository;
        this.boardLikesRepository = boardLikesRepository;
    }

    public ResponseDto<?> addBoardLike(Long boardId, String userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return addLike(board.getUser().getId(), boardId, new BoardLikes(user, board));
    }


    public ResponseDto<?> cancelBoardLike(Long boardId, String userId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return cancelLike(board.getId(), userId);
    }
}
