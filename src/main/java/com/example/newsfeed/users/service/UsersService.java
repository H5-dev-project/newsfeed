package com.example.newsfeed.users.service;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.jwt.JwtUtil;
import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.jwt.entity.RefreshToken;
import com.example.newsfeed.jwt.repository.RefreshTokenRepository;
import com.example.newsfeed.users.dto.request.DeleteRequestDto;
import com.example.newsfeed.users.dto.request.LoginRequestDto;
import com.example.newsfeed.users.dto.request.RegisterRequestDto;
import com.example.newsfeed.users.dto.request.UpdateRequestDto;
import com.example.newsfeed.users.dto.response.UsersResponseDto;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<UsersResponseDto> register(RegisterRequestDto dto) {
        usersRepository.findByEmail(dto.getEmail()).ifPresent(users -> {
            if (users.getIsDeleted()) {
                throw new IllegalStateException("해당 이메일은 탈퇴된 계정입니다.");
            }
            throw new DuplicateKeyException("중복된 이메일 입니다.");
        });

        if(usersRepository.findByPhone(dto.getPhone()).isPresent()){
            throw new DuplicateKeyException("중복된 전화번호 입니다.");
        }
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        Users users = new Users(
                dto.getEmail(),
                encryptedPassword,
                dto.getUsername(),
                dto.getBirth(),
                dto.getPhone(),
                dto.getGender()
        );

        Users savedUsers = usersRepository.save(users);
        UsersResponseDto response = new UsersResponseDto(
                savedUsers.getEmail(),
                savedUsers.getUsername(),
                savedUsers.getBirth(),
                savedUsers.getPhone(),
                savedUsers.getGender(),
                savedUsers.getCreatedAt(),
                savedUsers.getModifiedAt()
        );

        return ResponseDto.success(response);
    }

    @Transactional
    public TokenDto login(LoginRequestDto dto) {

        Users users = usersRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(dto.getPassword(), users.getPassword())){
            throw new BadCredentialsException("비밀번호가 올바르지 않습니다.");
        }

        TokenDto tokenDto = jwtUtil.createToken(users.getId());

        refreshTokenRepository.findByUsersId(users.getId()).ifPresentOrElse(
                existingToken -> existingToken.updateToken(tokenDto.getRefreshToken()), // 업데이트
                () -> refreshTokenRepository.save(new RefreshToken(tokenDto.getRefreshToken(), users)) // 새로 저장
        );

        return tokenDto;
    }

    @Transactional
    public ResponseDto<String> logout(AuthUsers authUsers) {

        RefreshToken refreshToken = refreshTokenRepository.findByUsersId(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰이 존재하지 않습니다."));

        refreshTokenRepository.delete(refreshToken);
        return ResponseDto.success("로그아웃이 완료되었습니다.");
    }

    @Transactional
    public ResponseDto<UsersResponseDto> update(UpdateRequestDto dto, AuthUsers authUsers) {
        Users users = usersRepository.findById(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(dto.getCurrentPassword(), users.getPassword())){
            throw new BadCredentialsException("현재 비밀번호가 일치하지 않습니다.");
        }

        users.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        UsersResponseDto response = new UsersResponseDto(
                users.getEmail(),
                users.getUsername(),
                users.getBirth(),
                users.getPhone(),
                users.getGender(),
                users.getCreatedAt(),
                users.getModifiedAt()
        );
        return ResponseDto.success(response);
    }

    @Transactional
    public ResponseDto<String> delete(DeleteRequestDto request, AuthUsers authUsers) {

        Users users = usersRepository.findById(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            throw new BadCredentialsException("비밀번호가 올바르지 않습니다.");
        }
        refreshTokenRepository.findByUsersId(users.getId()).ifPresent(refreshTokenRepository::delete);

        users.markDeleted();
        return ResponseDto.success("회원탈퇴가 완료되었습니다.");
    }


}
