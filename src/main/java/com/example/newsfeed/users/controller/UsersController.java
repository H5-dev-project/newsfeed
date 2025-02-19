package com.example.newsfeed.users.controller;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.annotation.UserSession;
import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.users.dto.request.*;
import com.example.newsfeed.users.service.UsersService;
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
    public ResponseEntity<ResponseDto<?>> logout(@UserSession AuthUsers authUsers){//
        return ResponseEntity.ok(usersService.logout(authUsers));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<ResponseDto<?>> updatePassword(@Valid @RequestBody PasswordUpdateRequestDto request,
                                                         @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.updatePassword(request, authUsers));
    }
    @PutMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<?>> updateProfile(
            @RequestPart("profile") @Valid ProfileUpdateRequestDto request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.updateProfile(request, file, authUsers));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<?>> delete(@RequestBody DeleteRequestDto request,
                                                 @UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.delete(request, authUsers));
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDto<?>> getMyPage(@UserSession AuthUsers authUsers){
        return ResponseEntity.ok(usersService.getMyPage(authUsers));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<?>> getUserPage(@PathVariable String userId){
        return ResponseEntity.ok(usersService.getUsersPage(userId));
    }
}
