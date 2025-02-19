package com.example.newsfeed.users.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class ProfileUpdateRequestDto {
    private String username;
    private LocalDate birth;
    private String phone;

    @JsonProperty
    @Schema(description = "성별", example = "1")
    private Byte gender;
    private String password;
}
