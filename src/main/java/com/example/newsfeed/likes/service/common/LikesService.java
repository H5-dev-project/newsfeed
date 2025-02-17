//package com.example.newsfeed.likes.service.common;
//
//import com.example.newsfeed.common.dto.ResponseDto;
//import com.example.newsfeed.likes.entity.BaseLike;
//import com.example.newsfeed.likes.repository.LikesRepository;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public abstract class LikesService<T extends BaseLike> {
//    protected final LikesRepository<T> likesRepository;
//
//    public ResponseDto<?> addLike(T like) {
//        likesRepository.save(like);
//        return ResponseDto.success("좋아요 등록 완료");
//    }
//
//    public ResponseDto<?> cancelLike(T like) {
//        likesRepository.delete(like);
//        return ResponseDto.success("좋아요 취소 완료");
//    }
//}
