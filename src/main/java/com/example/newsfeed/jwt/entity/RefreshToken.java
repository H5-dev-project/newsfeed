package com.example.newsfeed.jwt.entity;

import com.example.newsfeed.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    public RefreshToken(String refreshToken, Users users) {
        this.refreshToken = refreshToken;
        this.users = users;
    }

    public RefreshToken() {}

    public void updateToken(String newToken) {
        this.refreshToken = newToken;
    }
}