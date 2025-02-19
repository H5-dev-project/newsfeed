package com.example.newsfeed.users.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterRequestDto {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;
    @NotNull(message = "생일은 필수 입력값입니다.")
    private LocalDate birth;
    @NotBlank
    private String phone;

    @JsonProperty
    @Schema(description = "성별", example = "1")
    @NotNull
    private Byte gender;
}