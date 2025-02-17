//package com.example.newsfeed.likes.service;
//
//
//import com.example.newsfeed.common.dto.ResponseDto;
//import com.example.newsfeed.likes.entity.BoardLikes;
//import com.example.newsfeed.likes.repository.LikesRepository;
//import com.example.newsfeed.likes.service.common.LikesService;
//import com.example.newsfeed.users.entity.Users;
//import com.example.newsfeed.users.repository.UsersRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BoardLikesService extends LikesService<BoardLikes> {
//    private final BoardRepository boardRepository;
//    private final UsersRepository usersRepository;
//
//    public BoardLikesService(LikesRepository<BoardLikes> likesRepository
//    , BoardRepository boardRepository, UsersRepository usersRepository) {
//        super(likesRepository);
//        this.boardRepository = boardRepository;
//        this.usersRepository = usersRepository;
//    }
//
//    public ResponseDto<?> addBoardLike(Long boardId, String userId){
//        Users user = usersRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
//
//        if(userId.equals(board.getUsers().getId())){
//            throw new RuntimeException("본인이 작성한 게시글에는 좋아요를 누를 수 없습니다.");
//        }
//
//        if(likesRepository.findByBoardIdAndUserId(board.getId(), user.getId()).isPresent()){
//            throw new RuntimeException("좋아요는 한 번만 가능합니다.");
//        }
//
//        BoardLikes boardLikes = new BoardLikes(user, board);
//        return addLike(boardLikes);
//    }
//
//    public ResponseDto<?> cancelBoardLike(Long boardId, String userId){
//        boardRepository.findById(boardId)
//                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
//
//        BoardLikes boardLikes = super.likesRepository.findByBoardIdAndUserId(boardId, userId)
//                .orElseThrow(() -> new RuntimeException("좋아요 등록이 되어있지 않습니다."));
//
//        return cancelLike(boardLikes);
//    }
//}
