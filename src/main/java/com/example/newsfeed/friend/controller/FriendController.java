package com.example.newsfeed.friend.controller;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.friend.dto.FriendRequestDto;
import com.example.newsfeed.friend.dto.FriendResponseDto;
import com.example.newsfeed.friend.service.FriendShipService;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.users.service.UsersService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendShipService friendShipService;
    private final UsersService usersService;

    @PostMapping("/request") //친구 요청
    public ResponseEntity<ResponseDto<FriendResponseDto>> request(@RequestBody FriendRequestDto dto, @Parameter(hidden = true) @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(friendShipService.request(dto, authUsers.getUserId()));
    }

    // 친구 요청 수락
    @PatchMapping("/accept/{id}")
    public ResponseEntity<ResponseDto<FriendResponseDto>> acceptFriend(@RequestBody FriendRequestDto dto, @Parameter(hidden = true) @UserSession AuthUsers authUsers) {
        return ResponseEntity.ok(friendShipService.acceptFriend(dto, authUsers.getUserId()));
    }

    // 친구 요청 거절
    @PatchMapping("/reject/{id}")
    public ResponseEntity<ResponseDto<FriendResponseDto>> rejectFriend(@RequestBody FriendRequestDto dto, @Parameter(hidden = true) @UserSession AuthUsers authUsers) {
        return ResponseEntity.ok(friendShipService.rejectFriend(dto, authUsers.getUserId()));
    }

    @GetMapping//다건 조회
    public ResponseEntity<List<FriendResponseDto>> findAll(@Parameter(hidden = true) @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(friendShipService.findAll(authUsers.getUserId()));
    }

    @GetMapping("/{id}")//단건 조회
    public ResponseEntity<FriendResponseDto> findOne(@PathVariable Long id){
        return ResponseEntity.ok(friendShipService.findOne(id));
    }

}
