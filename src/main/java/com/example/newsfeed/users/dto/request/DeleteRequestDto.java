package com.example.newsfeed.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DeleteRequestDto {
    @Schema(description = "비밀번호 확인", example = "password@A1")
    private String password;}