package com.example.newsfeed.comment.service;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UsersRepository usersRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<CommentResponseDto> save(String userId, String boardId, CommentSaveRequestDto dto) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));
        Comment comment = new Comment(board, user, dto.getContent());
        commentRepository.save(comment);

        return ResponseDto
                .success(new CommentResponseDto(comment.getId(),
                        user.getId(), board.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByBoardId(String boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getBoard().getId(),
                        comment.getUser().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        return new CommentResponseDto(
                comment.getId(),
                comment.getBoard().getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }

    @Transactional
    public CommentResponseDto update(Long id, String userId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }
        comment.update(dto.getContent());

        return new CommentResponseDto(
                comment.getId(),
                comment.getBoard().getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }

    @Transactional
    public void delete(Long id, String userId) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
