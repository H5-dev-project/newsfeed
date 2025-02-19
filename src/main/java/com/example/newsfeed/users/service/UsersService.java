package com.example.newsfeed.users.service;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.common.FileUtil;
import com.example.newsfeed.friend.repository.FriendRepository;
import com.example.newsfeed.jwt.JwtUtil;
import com.example.newsfeed.jwt.dto.TokenDto;
import com.example.newsfeed.jwt.entity.AuthUsers;
import com.example.newsfeed.jwt.entity.RefreshToken;
import com.example.newsfeed.jwt.repository.RefreshTokenRepository;
import com.example.newsfeed.users.dto.request.*;
import com.example.newsfeed.users.dto.response.MyPageResponseDto;
import com.example.newsfeed.users.dto.response.UsersPageBoardsResponseDto;
import com.example.newsfeed.users.dto.response.UsersPageResponseDto;
import com.example.newsfeed.users.dto.response.UsersResponseDto;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BoardRepository boardRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final FriendRepository friendRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final FileUtil fileUtil; // 파일 저장 유틸리티

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

        return ResponseDto.success(buildUsersResponse(savedUsers));
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
    public ResponseDto<UsersResponseDto> updatePassword(PasswordUpdateRequestDto dto, AuthUsers authUsers) {
        Users users = usersRepository.findById(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(dto.getCurrentPassword(), users.getPassword())){
            throw new BadCredentialsException("현재 비밀번호가 일치하지 않습니다.");
        }

        users.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        return ResponseDto.success(buildUsersResponse(users));
    }

    @Transactional
    public ResponseDto<UsersResponseDto> updateProfile(ProfileUpdateRequestDto dto, MultipartFile file, AuthUsers authUsers) {
        Users users = usersRepository.findById(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(dto.getPassword(), users.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String imagePath = "basicImage";

        if (file != null && !file.isEmpty()) {
            try {
                // 파일 저장 및 경로 반환
                imagePath = fileUtil.saveProfileImage(file, authUsers.getUserId());
            } catch (IOException e) {
                throw new IllegalStateException("파일 저장 중 오류가 발생했습니다.", e);
            }
        }

        users.updateProfile(
                dto.getUsername(),
                dto.getBirth(),
                dto.getPhone(),
                dto.getGender(),
                imagePath
        );

        return ResponseDto.success(buildUsersResponse(users));
    }

    @Transactional
    public ResponseDto<?> delete(DeleteRequestDto request, AuthUsers authUsers) {

        Users users = usersRepository.findById(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            throw new BadCredentialsException("비밀번호가 올바르지 않습니다.");
        }
        refreshTokenRepository.findByUsersId(users.getId()).ifPresent(refreshTokenRepository::delete);

        users.markDeleted();
        fileUtil.deleteAllUserImages(users.getId());
        return ResponseDto.success("회원탈퇴가 완료되었습니다.");
    }

    public ResponseDto<MyPageResponseDto> getMyPage(AuthUsers authUsers) {
        Users users = usersRepository.findById(authUsers.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        List<Board> boards = boardRepository.findByUser_Id(users.getId());
        int boardCount = boards.size();
        List<UsersPageBoardsResponseDto> boardDtos = boards.stream()
                .map(board -> new UsersPageBoardsResponseDto(board.getId(), board.getImages()))
                .toList();

        int friendCount = friendRepository.countByUsers_Id(users.getId());

        MyPageResponseDto response = new MyPageResponseDto(
                users.getEmail(),
                users.getUsername(),
                users.getBirth(),
                users.getPhone(),
                users.getGender(),
                users.getImagePath(),
                boardCount,
                friendCount,
                boardDtos,
                users.getCreatedAt(),
                users.getModifiedAt()
        );
        return ResponseDto.success(response);
    }
    public ResponseDto<UsersPageResponseDto> getUsersPage(AuthUsers authUsers, String userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        List<Board> boards = boardRepository.findByUser_Id(users.getId());
        boolean isFriend = isFriend(authUsers.getUserId(), userId);
        int friendCount = friendRepository.countByUsers_Id(users.getId());

        List<UsersPageBoardsResponseDto> boardDtos = boards.stream()
                .filter(board -> board.getVisibilityType() == 0 || (board.getVisibilityType() == 1 && isFriend))
                .map(board -> new UsersPageBoardsResponseDto(board.getId(), board.getImages()))
                .toList();

        UsersPageResponseDto response = new UsersPageResponseDto(
                users.getUsername(),
                users.getBirth(),
                users.getGender(),
                users.getImagePath(),
                boards.size(),
                friendCount,
                isFriend,
                boardDtos
        );
        return ResponseDto.success(response);
    }

    private UsersResponseDto buildUsersResponse(Users users) {
        UsersResponseDto response = new UsersResponseDto(
                users.getEmail(),
                users.getUsername(),
                users.getBirth(),
                users.getPhone(),
                users.getGender(),
                users.getCreatedAt(),
                users.getModifiedAt()
        );
        return response;
    }

    public boolean isFriend(String usersId, String friendId) {
        return friendRepository.existsByUsers_IdAndFriend_Id(usersId, friendId)
                || friendRepository.existsByFriend_IdAndUsers_Id(usersId, friendId);
    }
}
