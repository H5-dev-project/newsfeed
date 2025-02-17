package com.example.newsfeed.jwt;

import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.jwt.entity.AuthUsers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_TIME = 15 * 60 * 1000L;  // 15분
    private static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L;  // 7일
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public TokenDto createToken(String usersId) {
        Date date = new Date();
        String accessToken = Jwts.builder()
                .subject(usersId)
                .issuedAt(new Date())
                .expiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .subject(usersId)
                .issuedAt(new Date())
                .expiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .signWith(secretKey)
                .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new IllegalArgumentException("유효한 Bearer 토큰이 제공되지 않았습니다.");
    }

    public Claims extractClaims(String token) {
        try{
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e){
            throw e;
        } catch (JwtException e){
            throw new RuntimeException("JWT 처리 중 내부 서버 오류가 발생했습니다.");
        }
    }



    public AuthUsers getCurrentMemberInfo() {
        AuthUsers authUsers = (AuthUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUsers;
    }


}
