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
    private final ObjectMapper objectMapper;

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
            @Valid @RequestPart("profile") @io.swagger.v3.oas.annotations.media.Schema(type = "string" , example = "{\"username\" : \"kkk\" , \"birth\" : \"2020-02-20\" , \"phone\" : \"010-9876-5432\" , \"gender\" : 1 , \"password\" : \"password@A1\"}")
            String profileUpdateJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Parameter(hidden = true) @UserSession AuthUsers authUsers){
        try {
            // JSON을 BoardSaveRequestDto로 변환
            ProfileUpdateRequestDto dto = objectMapper.readValue(profileUpdateJson, ProfileUpdateRequestDto.class);
            return ResponseEntity.ok(usersService.updateProfile(dto, file, authUsers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
