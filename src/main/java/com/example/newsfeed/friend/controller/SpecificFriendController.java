package com.example.newsfeed.friend.controller;

import com.example.newsfeed.friend.dto.FriendRequestDto;
import com.example.newsfeed.friend.dto.FriendResponseDto;
import com.example.newsfeed.friend.service.FriendShipService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class SpecificFriendController {
    private final FriendShipService specificFriendService;

    @PostMapping("/request") //친구 요청
    public ResponseEntity<FriendResponseDto> request(@RequestBody FriendRequestDto dto){
        return ResponseEntity.ok(specificFriendService.request(dto));
    }

    // 친구 요청 수락
    @PostMapping("/accept/{id}")
    public ResponseEntity<String> acceptFriend(@PathVariable Long friend_id, HttpServletRequest request) {
        return ResponseEntity.ok(specificFriendService.acceptFriend(request, friend_id));
    }

    // 친구 요청 거절
    @PostMapping("/reject/{id}")
    public ResponseEntity<String> rejectFriend(@PathVariable Long friend_id, HttpServletRequest request) {
        return ResponseEntity.ok(specificFriendService.rejectFriend(request, friend_id));
    }

    @GetMapping//다건 조회
    public ResponseEntity<List<FriendResponseDto>> findAll(){
        return ResponseEntity.ok(specificFriendService.findAll());
    }

    @GetMapping("/{id}")//단건 조회
    public ResponseEntity<FriendResponseDto> findOne(@PathVariable Long id){
        return ResponseEntity.ok(specificFriendService.findOne(id));
    }

}
