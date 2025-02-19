package com.example.newsfeed.users.controller;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.users.dto.request.*;
import com.example.newsfeed.users.service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<?>> register(@Valid @RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(usersService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> login(@Valid @RequestBody LoginRequestDto request, HttpServletResponse response){
        TokenDto tokenDto = usersService.login(request);
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Set-Cookie", "Refresh-Token="+ tokenDto.getRefreshToken() + "; path=/; HttpOnly; SameSite=Strict"); //Secure; 테스트를 위해 제거


        return ResponseEntity.ok(ResponseDto.success("로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<?>> logout(@Parameter(hidden = true) @UserSession AuthUsers authUsers){//
        return ResponseEntity.ok(usersService.logout(authUsers));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ResponseDto<?>> updatePassword(@Valid @RequestBody PasswordUpdateRequestDto request,
                                                         @Parameter(hidden = true) @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.updatePassword(request, authUsers));
    }
    @PutMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<?>> updateProfile(
            @RequestPart("profile") @Valid String profileUpdateJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @UserSession AuthUsers authUsers){
        // JSON을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ProfileUpdateRequestDto dto;
        try {
            dto = objectMapper.readValue(profileUpdateJson, ProfileUpdateRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 파싱 중 오류 발생: " + e.getMessage());
        }
        return ResponseEntity.ok(usersService.updateProfile(dto, file, authUsers));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<?>> delete(@RequestBody DeleteRequestDto request,
                                                 @Parameter(hidden = true) @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.delete(request, authUsers));
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDto<?>> getMyPage(@Parameter(hidden = true) @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.getMyPage(authUsers));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<?>> getUserPage(@Parameter(hidden = true) @UserSession AuthUsers authUsers, @PathVariable String userId){
        return ResponseEntity.ok(usersService.getUsersPage(authUsers, userId));
    }
}
