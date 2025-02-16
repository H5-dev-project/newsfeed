package com.example.newsfeed.likes.service.common;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.likes.entity.BaseLike;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class AbstractLikesService<T extends BaseLike, R extends JpaRepository<T, Long>> {
    protected final R likeRepository;

    public ResponseDto<?> addLike(T like) {
        likeRepository.save(like);
        return ResponseDto.success("좋아요 등록 완료");
    }

    public ResponseDto<?> cancelLike(T like) {
        likeRepository.delete(like.getId());
        return ResponseDto.success("좋아요 취소 완료");
    }
}
