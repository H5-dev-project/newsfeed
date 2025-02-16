package com.example.newsfeed.likes.service;


import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.BoardLikes;
import com.example.newsfeed.likes.repository.LikesRepository;
import com.example.newsfeed.likes.service.common.AbstractLikesService;
import org.springframework.stereotype.Service;

@Service
public class BoardLikesService extends AbstractLikesService<BoardLikes, LikesRepository<BoardLikes>> {

    private final BoardService boardService;
    private final UsersService usersService;

    public BoardLikesService(LikesRepository<BoardLikes> likesRepository,
                             BoardService boardService,
                             UsersService usersService) {
        super(likesRepository);
        this.boardService = boardService;
        this.usersService = usersService;
    }


    public ResponseDto<?> addBoardLike(Long boardId, String userId){
        Board board = boardService.findById(boardId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        Users user = usersService.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(likeRepository.findByBoardIdAndUserId(boardId, userId).isPresent()){
            throw new RuntimeException("좋아요는 한 번만 가능합니다.");
        }

        BoardLikes boardLikes = new BoardLikes(user, board);
        return addLike(boardLikes);
    }

    public ResponseDto<?> cancelBoardLike(Long boardId, Long id){

        boardService.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        BoardLikes boardLikes = likeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("좋아요 등록이 되어있지 않습니다."));

        return cancelLike(boardLikes);
    }
}
