package com.example.newsfeed.users.controller;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.users.dto.request.DeleteRequestDto;
import com.example.newsfeed.users.dto.request.LoginRequestDto;
import com.example.newsfeed.users.dto.request.RegisterRequestDto;
import com.example.newsfeed.users.dto.request.UpdateRequestDto;
import com.example.newsfeed.users.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto<?>> logout(){
        return ResponseEntity.ok(usersService.logout());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<?>> update(@Valid @RequestBody UpdateRequestDto request){
        return ResponseEntity.ok(usersService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<?>> delete(@RequestBody DeleteRequestDto request){
        return ResponseEntity.ok(usersService.delete(request));
    }
}
