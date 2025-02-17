package com.example.newsfeed.jwt.service;

import com.example.newsfeed.jwt.JwtUtil;
import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.jwt.entity.RefreshToken;
import com.example.newsfeed.jwt.repository.RefreshTokenRepository;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;

    public TokenDto refresh(String token) {
        Claims claims = jwtUtil.extractClaims(token);

        if (claims.getExpiration().before(new Date())) {
            throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
        }

        Users users = usersRepository.findById(claims.getSubject())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        RefreshToken storedRefreshToken = refreshTokenRepository.findByUsersId(users.getId())
                .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰을 찾을 수 없습니다."));

        if(!storedRefreshToken.getRefreshToken().equals(token)) {
            log.error("비정상적인 접근 감지");
            refreshTokenRepository.delete(storedRefreshToken);
            throw new IllegalArgumentException("리프레시 토큰이 일치하지 않습니다.");
        }

        refreshTokenRepository.delete(storedRefreshToken);

        TokenDto newTokens = jwtUtil.createToken(users.getId());

        //새로운 Refresh Token db에 저장
        RefreshToken newRefreshToken = new RefreshToken(newTokens.getRefreshToken(), users);
        refreshTokenRepository.save(newRefreshToken);

        return newTokens;
    }
}
