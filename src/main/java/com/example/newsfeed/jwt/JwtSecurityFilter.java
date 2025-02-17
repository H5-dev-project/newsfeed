package com.example.newsfeed.jwt;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (PATH_MATCHER.match("/api/users/register", requestURI) ||
                PATH_MATCHER.match("/api/users/login", requestURI) ||
                PATH_MATCHER.match("/api/auth/refresh", requestURI)
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String jwt = jwtUtil.substringToken(authorization);
            try {
                Claims claims = jwtUtil.extractClaims(jwt);

                String userId = claims.getSubject();
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    AuthUsers authUsers = new AuthUsers(userId);

                    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUsers);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT_EXPIRED", "액세스 토큰이 만료되었습니다.");
                return;
            } catch (MalformedJwtException | SecurityException | UnsupportedJwtException | IllegalArgumentException e) {
                sendErrorResponse(response, HttpStatus.BAD_REQUEST, "INVALID_JWT", "잘못된 JWT 형식입니다.");
                return;
            } catch (Exception e) {
                sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "서버 내부 오류가 발생했습니다.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String errorCode, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseDto<?> errorResponse = ResponseDto.fail(status, errorCode, message);
        String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }

}
