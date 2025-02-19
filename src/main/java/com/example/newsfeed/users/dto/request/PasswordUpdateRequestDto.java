package com.example.newsfeed.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {
    @Schema(description = "현재 비밀번호", example = "password@A1")
    @NotBlank(message = "공백일 수 없습니다.")
    private String currentPassword;
    @Schema(description = "새 비밀번호", example = "newPassword@A1")
    @NotBlank(message = "공백일 수 없습니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String newPassword;
}