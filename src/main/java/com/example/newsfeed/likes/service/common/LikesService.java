package com.example.newsfeed.likes.service.common;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.BaseLike;
import com.example.newsfeed.likes.repository.BoardLikesRepository;
import com.example.newsfeed.likes.repository.CommentLikesRepository;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class LikesService<T extends BaseLike>{
    protected final JpaRepository<T, Long> likesRepository;
    protected final UsersRepository usersRepository;

    public ResponseDto<?> addLike(String userId, Long entityId, T likeEntity) {
        if(userId.equals(likeEntity.getUsers().getId())){
            throw new IllegalArgumentException("본인이 작성한 글에는 좋아요를 누를 수 없습니다.");
        }

        if(findLike(entityId, userId).isPresent()) {
            throw new IllegalArgumentException("좋아요는 한 번만 가능합니다.");
        };

        likesRepository.save(likeEntity);
        return ResponseDto.success("좋아요 등록 완료");
    }

    public ResponseDto<?> cancelLike(Long entityId, String userId){
        T likeEntity = findLike(entityId, userId)
                .orElseThrow(() -> new IllegalArgumentException("좋아요 등록이 되어있지 않습니다."));

        likesRepository.delete(likeEntity);
        return ResponseDto.success("좋아요 취소 완료");
    }

    private Optional<T> findLike(Long entityId, String userId){
        if(likesRepository instanceof BoardLikesRepository boardLikesRepository){
            return (Optional<T >) boardLikesRepository.findByBoardIdAndUsersId(entityId, userId);
        }else if (likesRepository instanceof CommentLikesRepository commentLikesRepository) {
            return (Optional<T >) commentLikesRepository.findByCommentIdAndUsersId(entityId, userId);
        }

        return Optional.empty();
    }

}
