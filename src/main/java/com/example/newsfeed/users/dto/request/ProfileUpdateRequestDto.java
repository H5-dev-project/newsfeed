package com.example.newsfeed.users.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class ProfileUpdateRequestDto {
    @Schema(description = "유저이름", example = "aaa")
    private String username;

    @Schema(description = "생년월일", example = "2025-02-19")
    private LocalDate birth;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @JsonProperty
    @Schema(description = "성별", example = "1")
    private Byte gender;

    @Schema(description = "비밀번호 확인", example = "password@A1")
    private String password;
}
