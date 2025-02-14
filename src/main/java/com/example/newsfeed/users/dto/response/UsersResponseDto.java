package com.example.newsfeed.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UsersResponseDto {

    private final String email;

    private final String username;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private final LocalDate birth;

    private final String phone;

    private final Byte gender;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime modifiedAt;
}
