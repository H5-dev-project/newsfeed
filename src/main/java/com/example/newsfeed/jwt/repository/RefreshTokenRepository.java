package com.example.newsfeed.jwt.repository;

import com.example.newsfeed.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUsersId(String memberId);

}
