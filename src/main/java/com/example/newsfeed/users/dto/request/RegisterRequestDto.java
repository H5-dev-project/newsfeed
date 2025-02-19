package com.example.newsfeed.users.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterRequestDto {
    @Schema(description = "이메일", example = "asd123@naver.com")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
    @Schema(description = "비밀번호", example = "password@A")
    @NotBlank
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Schema(description = "유저이름", example = "aaa")
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @Schema(description = "생년월일", example = "2025-02-19")
    @NotNull(message = "생일은 필수 입력값입니다.")
    private LocalDate birth;

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank
    private String phone;

    @JsonProperty
    @Schema(description = "성별", example = "1")
    @NotNull
    private Byte gender;
}