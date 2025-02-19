package com.example.newsfeed.users.dto.request;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class ProfileUpdateRequestDto {
    private String username;
    private LocalDate birth;
    private String phone;
    private Byte gender;
    private String password;
}
